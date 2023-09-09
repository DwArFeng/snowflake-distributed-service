package com.dwarfeng.sfds.api.integration.subgrade;

import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.stack.exception.GenerateException;
import com.dwarfeng.subgrade.stack.generation.Generator;

import java.util.List;

/**
 * 基于 Snowflake 实现的 Long 生成器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class SnowflakeLongGenerator implements Generator<Long> {

    private final GenerateService generateService;

    public SnowflakeLongGenerator(GenerateService generateService) {
        this.generateService = generateService;
    }

    @Override
    public Long generate() throws GenerateException {
        try {
            return generateService.generateLong();
        } catch (Exception e) {
            throw new GenerateException(e);
        }
    }

    @Override
    public List<Long> batchGenerate(int size) throws GenerateException {
        try {
            return generateService.generateLong(size);
        } catch (Exception e) {
            throw new GenerateException(e);
        }
    }

    @Override
    public String toString() {
        return "SnowflakeLongGenerator{" +
                "generateService=" + generateService +
                '}';
    }
}
