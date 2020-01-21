package com.dwarfeng.sfds.rpc.impl;

import com.dwarfeng.sfds.rpc.LongIdRpc;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LongIdRpcImpl implements LongIdRpc {

    @Autowired
    @Qualifier("longIdService")
    private LongIdService delegate;

    @Override
    @BehaviorAnalyse
    public long nextLongId() throws ServiceException {
        return delegate.nextLongId();
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey nextLongIdKey() throws ServiceException {
        return delegate.nextLongIdKey();
    }
}
