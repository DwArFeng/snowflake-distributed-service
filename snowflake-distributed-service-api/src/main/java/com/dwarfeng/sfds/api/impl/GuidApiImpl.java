package com.dwarfeng.sfds.api.impl;

import com.dwarfeng.sfds.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.sfds.stack.exception.ServiceException;
import com.dwarfeng.sfds.stack.service.GuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GuidApiImpl implements GuidService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("guidService")
    private GuidService delegate;

    @Override
    @TimeAnalyse
    public long nextGuid() throws ServiceException {
        return delegate.nextGuid();
    }
}
