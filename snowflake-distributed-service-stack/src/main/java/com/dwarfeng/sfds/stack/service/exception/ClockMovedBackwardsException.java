package com.dwarfeng.sfds.stack.service.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 时钟回拨异常。
 *
 * <p>
 * 该异常表示时钟发生了回拨，导致无法生成 ID。通常情况下，ID 生成器会拒绝生成 ID，
 *
 * <p>
 * 该异常由于位置不符合规范，因此使用 {@link com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException} 代替。
 * 但是，对于某些引用了该项目，并捕获该异常的项目，仍然需要保留该异常。<br>
 * 可以通过在项目中增加系统参数 <code>-Dsnowflake.useDeprecatedStackServiceException=true</code>，
 * 使项目在遇到时钟回拨问题时，仍然抛出该异常，而不是抛出新的异常。
 *
 * @author DwArFeng
 * @see com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException
 * @since 1.0.0
 * @deprecated 该异常由于位置不符合规范，
 * 因此使用 {@link com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException} 代替。
 */
// 为了兼容旧版本，保留该异常的使用。
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class ClockMovedBackwardsException extends HandlerException {

    private static final long serialVersionUID = 8409003384648965626L;

    private final long duration;

    public ClockMovedBackwardsException(long duration) {
        this.duration = duration;
    }

    public ClockMovedBackwardsException(Throwable cause, long duration) {
        super(cause);
        this.duration = duration;
    }

    @Override
    public String getMessage() {
        return "Clock moved backwards. Refusing to generate id for " + duration + " milliseconds";
    }
}
