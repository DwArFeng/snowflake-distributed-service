package com.dwarfeng.sfds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 解析结果。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
public class FastJsonResolveResult implements Bean {

    private static final long serialVersionUID = 661685003462265140L;

    public static FastJsonResolveResult of(ResolveResult resolveResult) {
        if (Objects.isNull(resolveResult)) {
            return null;
        } else {
            return new FastJsonResolveResult(
                    resolveResult.getOriginalId(),
                    resolveResult.getSequence(),
                    resolveResult.getWorkerId(),
                    resolveResult.getDatacenterId(),
                    resolveResult.getTimestampDelta(),
                    resolveResult.getTimestamp(),
                    resolveResult.getTwepoch()
            );
        }
    }

    @JSONField(name = "original_id", ordinal = 1)
    private long originalId;

    @JSONField(name = "sequence", ordinal = 2)
    private long sequence;

    @JSONField(name = "worker_id", ordinal = 3)
    private long workerId;

    @JSONField(name = "datacenter_id", ordinal = 4)
    private long datacenterId;

    @JSONField(name = "timestamp_delta", ordinal = 5)
    private long timestampDelta;

    @JSONField(name = "timestamp", ordinal = 6)
    private long timestamp;

    @JSONField(name = "twepoch", ordinal = 7)
    private long twepoch;

    public FastJsonResolveResult() {
    }

    public FastJsonResolveResult(
            long originalId, long sequence, long workerId, long datacenterId, long timestampDelta, long timestamp,
            long twepoch
    ) {
        this.originalId = originalId;
        this.sequence = sequence;
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.timestampDelta = timestampDelta;
        this.timestamp = timestamp;
        this.twepoch = twepoch;
    }

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getTimestampDelta() {
        return timestampDelta;
    }

    public void setTimestampDelta(long timestampDelta) {
        this.timestampDelta = timestampDelta;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTwepoch() {
        return twepoch;
    }

    public void setTwepoch(long twepoch) {
        this.twepoch = twepoch;
    }

    @Override
    public String toString() {
        return "FastJsonResolveResult{" +
                "originalId=" + originalId +
                ", sequence=" + sequence +
                ", workerId=" + workerId +
                ", datacenterId=" + datacenterId +
                ", timestampDelta=" + timestampDelta +
                ", timestamp=" + timestamp +
                ", twepoch=" + twepoch +
                '}';
    }
}
