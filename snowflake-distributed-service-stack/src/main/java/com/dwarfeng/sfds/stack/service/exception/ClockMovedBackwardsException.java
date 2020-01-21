package com.dwarfeng.sfds.stack.service.exception;

public class ClockMovedBackwardsException extends Exception {

    private static final long serialVersionUID = 2787926000953950500L;

    public ClockMovedBackwardsException() {
    }

    public ClockMovedBackwardsException(String message) {
        super(message);
    }

    public ClockMovedBackwardsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClockMovedBackwardsException(Throwable cause) {
        super(cause);
    }
}
