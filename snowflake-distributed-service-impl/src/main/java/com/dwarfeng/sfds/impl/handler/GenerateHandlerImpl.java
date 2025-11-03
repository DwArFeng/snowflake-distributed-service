package com.dwarfeng.sfds.impl.handler;

import com.dwarfeng.sfds.sdk.util.SnowflakeConstants;
import com.dwarfeng.sfds.sdk.util.SystemPropertyConstants;
import com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException;
import com.dwarfeng.sfds.stack.handler.GenerateHandler;
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
 * SnowFlake 的结构如下（每部分用-分开）：<br>
 * <code>0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 </code>
 * <ul>
 *     <li>
 *         1 位标识，由于 long 基本类型在 Java 中是带符号的，最高位是符号位，正数是 0，负数是 1，所以id一般是正数，最高位是 0。
 *     </li>
 *     <li>
 *         41 位时间截（毫秒级），注意，41 位时间截不是存储当前时间的时间截，
 *         而是存储时间截的差值（当前时间截 - 开始时间截得到的值），这里的的开始时间截，一般是我们的 ID 生成器开始使用的时间，
 *         由我们程序来指定的（如下下面程序 IdWorker 类的 startTime 属性）。
 *         41位的时间截，可以使用 69 年，年 <code>T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69</code>。
 *     </li>
 *     <li>
 *         10 位的数据机器位，可以部署在 1024 个节点，包括 5 位 datacenterId 和 5 位 workerId。
 *     </li>
 *     <li>
 *         12 位序列，毫秒内的计数，12 位的计数顺序号支持每个节点每毫秒 （同一机器，同一时间截） 产生 4096 个 ID 序号。
 *     </li>
 * </ul>
 * 以上比特位加起来刚好 64 位，为一个 {@link Long} 型。
 *
 * <p>
 * SnowFlake 的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生 ID 碰撞 （由数据中心 ID 和机器 ID 作区分），
 * 并且效率较高，经测试，SnowFlake 每秒能够产生 26 万 ID 左右。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Component
public class GenerateHandlerImpl implements GenerateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateHandlerImpl.class);

    private static final boolean USE_DEPRECATED_CLOCK_MOVED_BACKWARDS_EXCEPTION = Boolean.parseBoolean(
            System.getProperty(
                    SystemPropertyConstants.USE_DEPRECATED_STACK_SERVICE_EXCEPTION,
                    Boolean.FALSE.toString()
            )
    );

    /**
     * 工作机器 ID (0~31)。
     */
    @Value("${snowflake.worker_id}")
    private long workerId;

    /**
     * 数据中心 ID (0~31)。
     */
    @Value("${snowflake.datacenter_id}")
    private long datacenterId;

    /**
     * 开始时间截（毫秒）。
     * <p>
     * 用于计算生成 ID 中的时间戳部分，计算公式为：生成 ID 中的时间戳部分 = (当前时间戳 - twepoch) << 22
     * 如果未配置此项，将使用 {@link SnowflakeConstants#DEFAULT_TWEPOCH} 的默认值。
     */
    // 该单词系专有术语，忽略相关的拼写警告。
    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    @Value("${snowflake.twepoch:#{T(com.dwarfeng.sfds.sdk.util.SnowflakeConstants).DEFAULT_TWEPOCH}}")
    private long twepoch;

    /**
     * 毫秒内序列 (0~4095)。
     */
    private long sequence = 0L;

    /**
     * 上次生成 ID 的时间截。
     */
    private long lastTimestamp = -1L;

    @PostConstruct
    public void paramCheck() {
        if (workerId > SnowflakeConstants.MAX_WORKER_ID || workerId < 0) {
            LOGGER.error("Worker ID 不能大于 {} 或者小于 0, 将抛出异常...", SnowflakeConstants.MAX_WORKER_ID);
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0", SnowflakeConstants.MAX_WORKER_ID
            ));
        }
        if (datacenterId > SnowflakeConstants.MAX_DATACENTER_ID || datacenterId < 0) {
            LOGGER.error("Datacenter ID 不能大于 {} 或者小于 0, 将抛出异常...", SnowflakeConstants.MAX_DATACENTER_ID);
            throw new IllegalArgumentException(String.format(
                    "datacenter Id can't be greater than %d or less than 0", SnowflakeConstants.MAX_DATACENTER_ID
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

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常。
        if (timestamp < lastTimestamp) {
            LOGGER.warn("检测到系统时钟回退, 服务将会在 {} 毫秒之内拒绝服务, 将会抛出异常...", lastTimestamp - timestamp);
            throwClockMovedBackwardsException(lastTimestamp - timestamp);
        }

        // 如果是同一时间生成的，则进行毫秒内序列。
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SnowflakeConstants.SEQUENCE_MASK;
            // 毫秒内序列溢出。
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳。
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置。
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截。
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID。
        return ((timestamp - twepoch) << SnowflakeConstants.TIMESTAMP_LEFT_SHIFT) //
                | (datacenterId << SnowflakeConstants.DATACENTER_ID_SHIFT) //
                | (workerId << SnowflakeConstants.WORKER_ID_SHIFT) //
                | sequence;
    }

    private synchronized List<Long> internalGenerate(int number) throws Exception {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常。
        if (timestamp < lastTimestamp) {
            LOGGER.warn("检测到系统时钟回退, 服务将会在 {} 毫秒之内拒绝服务, 将会抛出异常...", lastTimestamp - timestamp);
            throwClockMovedBackwardsException(lastTimestamp - timestamp);
        }

        List<Long> result = new ArrayList<>(number);

        // 对 number 进行 for 操作。
        for (int i = 0; i < number; i++) {
            // 如果是同一时间生成的，则进行毫秒内序列。
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & SnowflakeConstants.SEQUENCE_MASK;
                // 毫秒内序列溢出。
                if (sequence == 0) {
                    // 阻塞到下一个毫秒,获得新的时间戳。
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            // 时间戳改变，毫秒内序列重置。
            else {
                sequence = 0L;
            }
            // 移位并通过或运算拼到一起组成64位的ID。
            result.add(
                    ((timestamp - twepoch) << SnowflakeConstants.TIMESTAMP_LEFT_SHIFT) //
                            | (datacenterId << SnowflakeConstants.DATACENTER_ID_SHIFT) //
                            | (workerId << SnowflakeConstants.WORKER_ID_SHIFT) //
                            | sequence
            );

            // 上次生成ID的时间截。
            lastTimestamp = timestamp;
        }

        // 移位并通过或运算拼到一起组成64位的ID。
        return result;
    }

    /**
     * 抛出时钟回拨异常。
     *
     * <p>
     * 该方法通过判断项目的系统参数，决定抛出新的 {@link ClockMovedBackwardsException} 或是过时的
     * {@link com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException}。
     *
     * @param duration 时钟回拨的持续时间（毫秒）。
     * @throws ClockMovedBackwardsException                                           新的时钟回拨异常。
     * @throws com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException 过时的时钟回拨异常。
     */
    // 为了兼容旧版本，使用 @SuppressWarnings 注解来抑制过时警告。
    @SuppressWarnings("deprecation")
    private void throwClockMovedBackwardsException(long duration) throws
            ClockMovedBackwardsException, com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException {
        if (USE_DEPRECATED_CLOCK_MOVED_BACKWARDS_EXCEPTION) {
            throw new com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException(duration);
        } else {
            throw new ClockMovedBackwardsException(duration);
        }
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳。
     *
     * @param lastTimestamp 上次生成 ID 的时间截。
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
     * @return 当前时间（毫秒）。
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "GenerateHandlerImpl{" +
                "workerId=" + workerId +
                ", datacenterId=" + datacenterId +
                ", twepoch=" + twepoch +
                ", sequence=" + sequence +
                ", lastTimestamp=" + lastTimestamp +
                '}';
    }
}
