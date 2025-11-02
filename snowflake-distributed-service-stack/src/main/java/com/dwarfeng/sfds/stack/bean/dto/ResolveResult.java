package com.dwarfeng.sfds.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 解析结果。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
public class ResolveResult implements Dto {

    private static final long serialVersionUID = -1230254312751245673L;

    /**
     * 原始 ID。
     */
    private long originalId;

    /**
     * 序列号（0~4095）。
     */
    private long sequence;

    /**
     * 工作机器 ID（0~31）。
     */
    private long workerId;

    /**
     * 数据中心 ID（0~31）。
     */
    private long datacenterId;

    /**
     * 时间戳差值（毫秒）。
     */
    private long timestampDelta;

    /**
     * 实际时间戳（毫秒）。
     */
    private long timestamp;

    /**
     * 使用的基准时间戳（毫秒）。
     */
    private long twepoch;

    public ResolveResult() {
    }

    public ResolveResult(
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
        return "ResolveResult{" +
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
