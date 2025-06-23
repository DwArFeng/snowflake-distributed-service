package com.dwarfeng.sfds.sdk.util;

/**
 * 系统属性常量类。
 *
 * <p>
 * 该类中定义了一些系统属性的常量，主要用于在系统中获取系统属性的值。
 *
 * <p>
 * 以 <code>VALUE_</code> 开头的常量表示系统属性的值，指示一个固定的系统属性。<br>
 * 以 <code>FORMAT_</code> 开头的常量表示系统属性的格式化值，根据格式化参数的不同，可以指示不同的系统属性。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public final class SystemPropertyConstants {

    /**
     * 使用过时的 <code>stack.service.exception</code> 包中的异常。
     */
    public static final String USE_DEPRECATED_STACK_SERVICE_EXCEPTION = "snowflake.useDeprecatedStackServiceException";

    private SystemPropertyConstants() {
        throw new IllegalStateException("禁止实例化");
    }
}
