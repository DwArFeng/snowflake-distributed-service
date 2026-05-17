package com.dwarfeng.sfds.sdk.util;

import com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 sfds 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 null。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        // 添加异常映射。
        map.put(ClockMovedBackwardsException.class, ServiceExceptionCodes.CLOCK_MOVED_BACKWARDS);
        // 添加过时的 stack.service.exception 包中的异常映射。
        putDeprecatedStackServiceException(map);

        return map;
    }

    // 为了兼容旧版本，忽略相关警告。
    @SuppressWarnings("deprecation")
    private static void putDeprecatedStackServiceException(Map<Class<? extends Exception>, ServiceException.Code> map) {
        map.put(
                com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException.class,
                ServiceExceptionCodes.CLOCK_MOVED_BACKWARDS
        );
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
