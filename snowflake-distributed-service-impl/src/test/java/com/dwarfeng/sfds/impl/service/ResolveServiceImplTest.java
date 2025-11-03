package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.sdk.util.SnowflakeConstants;
import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.sfds.stack.service.ResolveService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ResolveServiceImplTest {

    @Autowired
    private ResolveService resolveService;

    @Autowired
    private GenerateService generateService;

    // 该单词系专有术语，忽略相关的拼写警告。
    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    @Value("${snowflake.twepoch:#{T(com.dwarfeng.sfds.sdk.util.SnowflakeConstants).TWEPOCH}}")
    private long twepoch;

    @Test
    public void testForResolveLong() throws ServiceException {
        // 生成一个 ID
        long id = generateService.generateLong();

        // 解析 ID
        ResolveResult result = resolveService.resolveLong(id);

        // 断言验证
        Assert.assertEquals("原始 ID 应该与输入 ID 一致", id, result.getOriginalId());
        Assert.assertTrue(
                "序列号应该在有效范围内",
                result.getSequence() >= 0 && result.getSequence() <= SnowflakeConstants.SEQUENCE_MASK
        );
        Assert.assertTrue(
                "工作机器 ID 应该在有效范围内",
                result.getWorkerId() >= 0 && result.getWorkerId() <= SnowflakeConstants.MAX_WORKER_ID
        );
        Assert.assertTrue(
                "数据中心 ID 应该在有效范围内",
                result.getDatacenterId() >= 0 && result.getDatacenterId() <= SnowflakeConstants.MAX_DATACENTER_ID
        );
        Assert.assertTrue("时间戳差值应该大于等于 0", result.getTimestampDelta() >= 0);
        Assert.assertEquals(
                "实际时间戳应该等于时间戳差值加上基准时间戳",
                result.getTimestampDelta() + result.getTwepoch(), result.getTimestamp()
        );
        Assert.assertEquals("基准时间戳应该等于 twepoch 值", twepoch, result.getTwepoch());

        // 验证解析结果与生成逻辑一致（反向计算验证）
        long reconstructedId = ((result.getTimestampDelta() << SnowflakeConstants.TIMESTAMP_LEFT_SHIFT)
                | (result.getDatacenterId() << SnowflakeConstants.DATACENTER_ID_SHIFT)
                | (result.getWorkerId() << SnowflakeConstants.WORKER_ID_SHIFT)
                | result.getSequence());
        Assert.assertEquals("通过解析结果重构的 ID 应该与原始 ID 一致", id, reconstructedId);
    }

    @Test
    public void testForResolveLongIdKey() throws ServiceException {
        // 生成一个 LongIdKey
        LongIdKey idKey = generateService.generateLongIdKey();
        long id = idKey.getLongId();

        // 解析 LongIdKey
        ResolveResult result = resolveService.resolveLongIdKey(idKey);

        // 断言验证
        Assert.assertEquals("原始 ID 应该与 LongIdKey 的 ID 一致", id, result.getOriginalId());
        Assert.assertTrue(
                "序列号应该在有效范围内",
                result.getSequence() >= 0 && result.getSequence() <= SnowflakeConstants.SEQUENCE_MASK
        );
        Assert.assertTrue(
                "工作机器 ID 应该在有效范围内",
                result.getWorkerId() >= 0 && result.getWorkerId() <= SnowflakeConstants.MAX_WORKER_ID
        );
        Assert.assertTrue(
                "数据中心 ID 应该在有效范围内",
                result.getDatacenterId() >= 0 && result.getDatacenterId() <= SnowflakeConstants.MAX_DATACENTER_ID
        );
        Assert.assertTrue("时间戳差值应该大于等于 0", result.getTimestampDelta() >= 0);
        Assert.assertEquals(
                "实际时间戳应该等于时间戳差值加上基准时间戳",
                result.getTimestampDelta() + result.getTwepoch(), result.getTimestamp()
        );
        Assert.assertEquals("基准时间戳应该等于 twepoch 值", twepoch, result.getTwepoch());

        // 验证解析结果与生成逻辑一致（反向计算验证）
        long reconstructedId = ((result.getTimestampDelta() << SnowflakeConstants.TIMESTAMP_LEFT_SHIFT)
                | (result.getDatacenterId() << SnowflakeConstants.DATACENTER_ID_SHIFT)
                | (result.getWorkerId() << SnowflakeConstants.WORKER_ID_SHIFT)
                | result.getSequence());
        Assert.assertEquals("通过解析结果重构的 ID 应该与原始 ID 一致", id, reconstructedId);
    }

    @Test
    public void testForResolveMultiple() throws ServiceException {
        // 生成多个 ID 并解析
        for (int i = 0; i < 10; i++) {
            long id = generateService.generateLong();
            ResolveResult result = resolveService.resolveLong(id);

            // 基本验证
            Assert.assertEquals("原始 ID 应该与输入 ID 一致", id, result.getOriginalId());
            Assert.assertTrue("序列号应该在有效范围内",
                    result.getSequence() >= 0 && result.getSequence() <= SnowflakeConstants.SEQUENCE_MASK
            );
            Assert.assertTrue(
                    "工作机器 ID 应该在有效范围内",
                    result.getWorkerId() >= 0 && result.getWorkerId() <= SnowflakeConstants.MAX_WORKER_ID
            );
            Assert.assertTrue(
                    "数据中心 ID 应该在有效范围内",
                    result.getDatacenterId() >= 0 && result.getDatacenterId() <= SnowflakeConstants.MAX_DATACENTER_ID
            );

            // 验证反向计算
            long reconstructedId = ((result.getTimestampDelta() << SnowflakeConstants.TIMESTAMP_LEFT_SHIFT)
                    | (result.getDatacenterId() << SnowflakeConstants.DATACENTER_ID_SHIFT)
                    | (result.getWorkerId() << SnowflakeConstants.WORKER_ID_SHIFT)
                    | result.getSequence());
            Assert.assertEquals("通过解析结果重构的 ID 应该与原始 ID 一致", id, reconstructedId);
        }
    }
}
