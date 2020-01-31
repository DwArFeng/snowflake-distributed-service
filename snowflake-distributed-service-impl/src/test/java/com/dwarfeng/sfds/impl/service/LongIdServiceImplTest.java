package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class LongIdServiceImplTest {

    @Autowired
    private LongIdService longIdService;

    @Test
    public void nextLongId() throws ServiceException {
        for (int i = 0; i < 100; i++) {
            CT.trace(longIdService.nextLongId());
        }
    }

    @Test
    public void nextLongIdKey() throws ServiceException {
        for (int i = 0; i < 100; i++) {
            CT.trace(longIdService.nextLongIdKey());
        }
    }
}