package com.dwarfeng.sfds.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    /**
     * 时钟后移异常。
     */
    public static final ServiceException.Code CLOCK_MOVED_BACKWARDS = new ServiceException.Code(100, "clock moved backwards");

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
