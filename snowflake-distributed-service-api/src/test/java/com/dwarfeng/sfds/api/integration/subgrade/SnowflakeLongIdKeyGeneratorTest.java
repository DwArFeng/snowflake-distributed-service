package com.dwarfeng.sfds.api.integration.subgrade;

import com.dwarfeng.dutil.basic.sdk.io.CT;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.GenerateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/sfds/api/spring/application-context*.xml")
public class SnowflakeLongIdKeyGeneratorTest {

    @Autowired
    private SnowflakeLongIdKeyGenerator generator;

    @Test
    public void testGenerate() throws GenerateException {
        for (int i = 0; i < 100; i++) {
            CT.trace(generator.generate());
        }
    }

    @Test
    public void testBatchGenerate() throws GenerateException {
        List<LongIdKey> longIdKeys = generator.batchGenerate(100);
        longIdKeys.forEach(CT::trace);
    }
}
