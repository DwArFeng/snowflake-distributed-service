package com.dwarfeng.sfds.impl.handler;

import com.dwarfeng.sfds.sdk.util.SnowflakeConstants;
import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.sfds.stack.handler.ResolveHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 解析处理器实现。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
@Component
public class ResolveHandlerImpl implements ResolveHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResolveHandlerImpl.class);

    /**
     * 开始时间截（毫秒）。
     * <p>
     * 用于计算解析 ID 中的时间戳部分，计算公式为：实际时间戳 = timestampDelta + twepoch
     * 如果未配置此项，将使用 {@link SnowflakeConstants#DEFAULT_TWEPOCH} 的默认值。
     */
    // 该单词系专有术语，忽略相关的拼写警告。
    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    @Value("${snowflake.twepoch:#{T(com.dwarfeng.sfds.sdk.util.SnowflakeConstants).DEFAULT_TWEPOCH}}")
    private long twepoch;

    @PostConstruct
    public void init() {
        LOGGER.debug("ResolveHandlerImpl 初始化完成, twepoch={}", twepoch);
    }

    @Override
    public ResolveResult resolveLong(long id) throws HandlerException {
        try {
            return internalResolve(id);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public ResolveResult resolveLongIdKey(LongIdKey idKey) throws HandlerException {
        try {
            return internalResolve(idKey.getLongId());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 内部解析方法。
     *
     * @param id 待解析的 ID。
     * @return 解析结果。
     */
    private ResolveResult internalResolve(long id) {
        // 提取序列号（最低12位）
        long sequence = id & SnowflakeConstants.SEQUENCE_MASK;

        // 提取工作机器 ID（第12-16位）
        long workerId = (id >> SnowflakeConstants.WORKER_ID_SHIFT) & SnowflakeConstants.MAX_WORKER_ID;

        // 提取数据中心 ID（第17-21位）
        long datacenterId = (id >> SnowflakeConstants.DATACENTER_ID_SHIFT) & SnowflakeConstants.MAX_DATACENTER_ID;

        // 提取时间戳差值（最高41位）
        long timestampDelta = id >> SnowflakeConstants.TIMESTAMP_LEFT_SHIFT;

        // 计算实际时间戳
        long timestamp = timestampDelta + twepoch;

        return new ResolveResult(id, sequence, workerId, datacenterId, timestampDelta, timestamp, twepoch);
    }

    @Override
    public String toString() {
        return "ResolveHandlerImpl{" +
                "twepoch=" + twepoch +
                '}';
    }
}

