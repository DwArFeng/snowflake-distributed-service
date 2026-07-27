package com.dwarfeng.sfds.sdk.exception;

import com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Snowflake 模块服务异常帮助类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 Snowflake 模块默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速搭建异常目标映射。映射保存异常代码供应器，调用方在映射异常时解析当前代码快照。
     *
     * @param map 指定的映射，允许为 null。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, Supplier<ServiceException.Code>> putDefaultDestination(
            Map<Class<? extends Exception>, Supplier<ServiceException.Code>> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(ClockMovedBackwardsException.class, ServiceExceptionCodeSuppliers.CLOCK_MOVED_BACKWARDS);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
