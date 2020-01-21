package com.dwarfeng.sfds.integration.subgrade;

import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.KeyFetchException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeLongIdKeyFetcher implements KeyFetcher<LongIdKey> {

    @Autowired
    @Qualifier("longIdService")
    private LongIdService delegate;

    @Override
    public LongIdKey fetchKey() throws KeyFetchException {
        try {
            return delegate.nextLongIdKey();
        } catch (ServiceException e) {
            throw new KeyFetchException(e);
        }
    }
}
