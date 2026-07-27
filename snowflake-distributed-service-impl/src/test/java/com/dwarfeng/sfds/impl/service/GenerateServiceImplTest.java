package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.dutil.basic.sdk.io.CT;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/sfds/impl/spring/application-context*.xml")
public class GenerateServiceImplTest {

    @Autowired
    private GenerateService generateService;

    @Test
    public void testGenerateLong() throws ServiceException {
        for (int i = 0; i < 100; i++) {
            CT.trace(generateService.generateLong());
        }
    }

    @Test
    public void testGenerateLongIdKey() throws ServiceException {
        for (int i = 0; i < 100; i++) {
            CT.trace(generateService.generateLongIdKey());
        }
    }

    @Test
    public void testGenerateLongWithSize() throws ServiceException {
        List<Long> longs = generateService.generateLong(100);
        longs.forEach(CT::trace);
    }

    @Test
    public void testGenerateLongIdKeyWithSize() throws ServiceException {
        List<LongIdKey> longIdKeys = generateService.generateLongIdKey(100);
        longIdKeys.forEach(CT::trace);
    }
}
