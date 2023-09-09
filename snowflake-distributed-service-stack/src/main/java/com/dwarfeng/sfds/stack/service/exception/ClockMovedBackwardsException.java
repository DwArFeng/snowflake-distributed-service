package com.dwarfeng.sfds.stack.service.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 时钟回拨异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
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
