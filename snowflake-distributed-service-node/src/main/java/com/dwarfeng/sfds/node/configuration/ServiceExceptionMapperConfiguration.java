package com.dwarfeng.sfds.node.configuration;

import com.dwarfeng.sfds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        // 添加异常映射。
        destination.put(ClockMovedBackwardsException.class, ServiceExceptionCodes.CLOCK_MOVED_BACKWARDS);
        // 添加过时的 stack.service.exception 包中的异常映射。
        putDeprecatedStackServiceException(destination);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }

    // 为了兼容旧版本，忽略相关警告。
    @SuppressWarnings("deprecation")
    private static void putDeprecatedStackServiceException(Map<Class<? extends Exception>, ServiceException.Code> destination) {
        destination.put(com.dwarfeng.sfds.stack.service.exception.ClockMovedBackwardsException.class, ServiceExceptionCodes.CLOCK_MOVED_BACKWARDS);
    }
}
