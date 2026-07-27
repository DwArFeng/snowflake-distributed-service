package com.dwarfeng.sfds.stack.exception;

import com.dwarfeng.sfds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.sfds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 时钟回拨异常。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class ClockMovedBackwardsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -8838967308507708029L;

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
        return StackMessages.message(StackMessageKey.CLOCK_MOVED_BACKWARDS_DETAIL, duration);
    }
}
