package com.dwarfeng.sfds.api.integration.subgrade;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.KeyFetchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class SnowFlakeLongIdKeyFetcherTest {

    @Autowired
    private KeyFetcher<LongIdKey> keyKeyFetcher;

    @Test
    public void fetchKey() throws KeyFetchException {
        for (int i = 0; i < 100; i++) {
            CT.trace(keyKeyFetcher.fetchKey());
        }
    }

    @Test
    public void batchFetchKey() throws KeyFetchException {
        List<LongIdKey> longIdKeys = keyKeyFetcher.batchFetchKey(100);
        longIdKeys.forEach(CT::trace);
    }
}
