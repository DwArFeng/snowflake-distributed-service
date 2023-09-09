package com.dwarfeng.sfds.api.integration.subgrade;

import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.GenerateException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;

import java.util.List;

/**
 * 基于 Snowflake 实现的 LongIdKey 生成器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class SnowflakeLongIdKeyGenerator implements KeyGenerator<LongIdKey> {

    private final GenerateService generateService;

    public SnowflakeLongIdKeyGenerator(GenerateService generateService) {
        this.generateService = generateService;
    }

    @Override
    public LongIdKey generate() throws GenerateException {
        try {
            return generateService.generateLongIdKey();
        } catch (Exception e) {
            throw new GenerateException(e);
        }
    }

    @Override
    public List<LongIdKey> batchGenerate(int size) throws GenerateException {
        try {
            return generateService.generateLongIdKey(size);
        } catch (Exception e) {
            throw new GenerateException(e);
        }
    }

    @Override
    public String toString() {
        return "SnowflakeLongIdKeyGenerator{" +
                "generateService=" + generateService +
                '}';
    }
}
