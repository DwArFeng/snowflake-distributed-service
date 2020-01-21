package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.sdk.util.SnowFlakeConstants;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
@Service
public class LongIdServiceImpl implements LongIdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongIdServiceImpl.class);

    @Autowired
    private ServiceExceptionMapper sem;

    /**
     * 工作机器ID(0~31)
     */
    @Value("${snowflake.workder_id}")
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
            LOGGER.error(String.format("Workder ID 不能大于 %d 或者小于 0, 将抛出异常...", SnowFlakeConstants.MAX_WORKER_ID));
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", SnowFlakeConstants.MAX_WORKER_ID));
        }
        if (datacenterId > SnowFlakeConstants.MAX_DATACENTER_ID || datacenterId < 0) {
            LOGGER.error(String.format("Datacenter ID 不能大于 %d 或者小于 0, 将抛出异常...", SnowFlakeConstants.MAX_DATACENTER_ID));
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", SnowFlakeConstants.MAX_DATACENTER_ID));
        }
    }

    @Override
    @BehaviorAnalyse
    public long nextLongId() throws ServiceException {
        return internalNextLong();
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey nextLongIdKey() throws ServiceException {
        return new LongIdKey(internalNextLong());
    }

    private synchronized long internalNextLong() throws ServiceException {
        try {
            long timestamp = timeGen();

            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                LOGGER.warn(String.format("检测到系统时钟回退, 服务将会在 %d 毫秒之内拒绝服务, 将会抛出异常...", lastTimestamp - timestamp));
                throw new ClockMovedBackwardsException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
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
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("生成序列时发生异常", LogLevel.WARN, sem, e);
        }
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
