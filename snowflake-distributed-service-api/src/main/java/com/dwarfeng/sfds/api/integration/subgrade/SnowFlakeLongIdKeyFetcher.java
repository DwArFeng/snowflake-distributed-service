package com.dwarfeng.sfds.api.integration.subgrade;

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

    // 由于许多依赖此工程的项目使用了此类的无参数构造器，因此无法将此处的 @Autowired 依赖装配改为构造器依赖装配。
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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
