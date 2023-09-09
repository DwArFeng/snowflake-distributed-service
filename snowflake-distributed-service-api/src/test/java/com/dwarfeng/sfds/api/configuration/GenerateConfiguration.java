package com.dwarfeng.sfds.api.configuration;

import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongGenerator;
import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateConfiguration {

    @Bean
    public SnowflakeLongGenerator snowflakeLongGenerator(GenerateService generateService) {
        return new SnowflakeLongGenerator(generateService);
    }

    @Bean
    public SnowflakeLongIdKeyGenerator snowflakeLongIdKeyGenerator(GenerateService generateService) {
        return new SnowflakeLongIdKeyGenerator(generateService);
    }
}
