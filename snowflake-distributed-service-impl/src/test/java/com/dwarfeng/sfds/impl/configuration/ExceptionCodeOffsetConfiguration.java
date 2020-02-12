package com.dwarfeng.sfds.impl.configuration;

import com.dwarfeng.sfds.sdk.util.ServiceExceptionCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ExceptionCodeOffsetConfiguration {

    @Value("${snowflake.exception_code_offset}")
    private int exceptionCodeOffset;
    @Value("${snowflake.exception_code_offset.subgrade}")
    private int subgradeExceptionCodeOffset;

    @PostConstruct
    public void init() {
        ServiceExceptionCodes.setExceptionCodeOffset(exceptionCodeOffset);
        com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.setExceptionCodeOffset(subgradeExceptionCodeOffset);
    }
}
