package com.dwarfeng.sfds.api.impl;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.sfds.api.GuidApi;
import com.dwarfeng.sfds.stack.exception.ServiceException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GuidApiImplTest {

    @Autowired
    private GuidApi guidApi;

    @Test
    public void nextGuid() throws ServiceException {
        for (int i = 0; i < 100; i++) {
            CT.trace(guidApi.nextGuid());
        }
    }
}