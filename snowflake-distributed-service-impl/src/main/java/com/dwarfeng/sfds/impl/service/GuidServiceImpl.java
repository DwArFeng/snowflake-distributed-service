package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.stack.exception.ServiceException;
import com.dwarfeng.sfds.stack.service.GuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuidServiceImpl implements GuidService {

    @Autowired
    private GuidServiceDelegate delegate;

    @Override
    public long nextGuid() throws ServiceException {
        return delegate.nextGuid();
    }
}
