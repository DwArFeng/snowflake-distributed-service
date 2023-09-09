package com.dwarfeng.sfds.impl.handler;

import com.dwarfeng.sfds.sdk.util.SnowFlakeConstants;
import com.dwarfeng.sfds.stack.handler.GenerateHandler;
import com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成处理器实现。
 *
 * <p>
 * <b>Twitter_Snowflake</b><br>
 * SnowFlake 的结构如下(每部分用-分开):<br>
 * <code>0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 </code><br>
 * <ul>
 *     <li>
 *         1 位标识，由于 long 基本类型在 Java 中是带符号的，最高位是符号位，正数是 0，负数是 1，所以id一般是正数，最高位是 0。
 *     </li>
 *     <li>
 *         41 位时间截(毫秒级)，注意，41 位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 *         得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，
 *         由我们程序来指定的（如下下面程序 IdWorker 类的startTime属性）。
 *         41位的时间截，可以使用 69 年，年 <code>T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69</code>。
 *     </li>
 *     <li>
 *         10 位的数据机器位，可以部署在 1024 个节点，包括 5 位 datacenterId 和 5 位 workerId。
 *     </li>
 *     <li>
 *         12 位序列，毫秒内的计数，12 位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生 4096 个 ID 序号。
 *     </li>
 * </ul>
 * 以上比特位加起来刚好 64 位，为一个 {@link Long} 型。
 *
 * <p>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生 ID 碰撞(由数据中心 ID 和机器 ID 作区分)，
 * 并且效率较高，经测试，SnowFlake 每秒能够产生 26 万 ID 左右。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Component
public class GenerateHandlerImpl implements GenerateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateHandlerImpl.class);

    /**
     * 工作机器ID(0~31)
     */
    @Value("${snowflake.worker_id}")
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    @Value("${snowflake.datacenter_id}")
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    @PostConstruct
    public void paramCheck() {
        if (workerId > SnowFlakeConstants.MAX_WORKER_ID || workerId < 0) {
            LOGGER.error(String.format(
                    "Worker ID 不能大于 %d 或者小于 0, 将抛出异常...", SnowFlakeConstants.MAX_WORKER_ID
            ));
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0", SnowFlakeConstants.MAX_WORKER_ID
            ));
        }
        if (datacenterId > SnowFlakeConstants.MAX_DATACENTER_ID || datacenterId < 0) {
            LOGGER.error(String.format(
                    "Datacenter ID 不能大于 %d 或者小于 0, 将抛出异常...", SnowFlakeConstants.MAX_DATACENTER_ID
            ));
            throw new IllegalArgumentException(String.format(
                    "datacenter Id can't be greater than %d or less than 0", SnowFlakeConstants.MAX_DATACENTER_ID
            ));
        }
    }

    @Override
    public long generateLong() throws HandlerException {
        try {
            return internalGenerate();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public LongIdKey generateLongIdKey() throws HandlerException {
        try {
            return new LongIdKey(internalGenerate());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public List<Long> generateLong(int size) throws HandlerException {
        try {
            return internalGenerate(size);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public List<LongIdKey> generateLongIdKey(int size) throws HandlerException {
        try {
            return internalGenerate(size).stream().map(LongIdKey::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private synchronized long internalGenerate() throws Exception {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            LOGGER.warn(String.format(
                    "检测到系统时钟回退, 服务将会在 %d 毫秒之内拒绝服务, 将会抛出异常...", lastTimestamp - timestamp
            ));
            throw new ClockMovedBackwardsException(lastTimestamp - timestamp);
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SnowFlakeConstants.SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - SnowFlakeConstants.TWEPOCH) << SnowFlakeConstants.TIMESTAMP_LEFT_SHIFT) //
                | (datacenterId << SnowFlakeConstants.DATACENTER_ID_SHIFT) //
                | (workerId << SnowFlakeConstants.WORKER_ID_SHIFT) //
                | sequence;
    }

    private synchronized List<Long> internalGenerate(int number) throws Exception {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            LOGGER.warn(String.format(
                    "检测到系统时钟回退, 服务将会在 %d 毫秒之内拒绝服务, 将会抛出异常...", lastTimestamp - timestamp
            ));
            throw new ClockMovedBackwardsException(lastTimestamp - timestamp);
        }

        List<Long> result = new ArrayList<>(number);

        // 对 number 进行 for 操作。
        for (int i = 0; i < number; i++) {
            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & SnowFlakeConstants.SEQUENCE_MASK;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }
            //移位并通过或运算拼到一起组成64位的ID
            result.add(
                    ((timestamp - SnowFlakeConstants.TWEPOCH) << SnowFlakeConstants.TIMESTAMP_LEFT_SHIFT) //
                            | (datacenterId << SnowFlakeConstants.DATACENTER_ID_SHIFT) //
                            | (workerId << SnowFlakeConstants.WORKER_ID_SHIFT) //
                            | sequence
            );

            //上次生成ID的时间截
            lastTimestamp = timestamp;
        }

        //移位并通过或运算拼到一起组成64位的ID
        return result;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳。
     *
     * @param lastTimestamp 上次生成ID的时间截。
     * @return 当前时间戳。
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间。
     *
     * @return 当前时间(毫秒)。
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "GenerateHandlerImpl{" +
                "workerId=" + workerId +
                ", datacenterId=" + datacenterId +
                ", sequence=" + sequence +
                ", lastTimestamp=" + lastTimestamp +
                '}';
    }
}
