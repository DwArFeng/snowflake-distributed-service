package com.dwarfeng.sfds.stack.exception;

import com.dwarfeng.sfds.base.sdk.i18n.MessageContext;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 时钟回拨异常测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ClockMovedBackwardsExceptionTest {

    @Test
    public void shouldResolveMessageFromCurrentMessageContext() {
        ClockMovedBackwardsException exception = new ClockMovedBackwardsException(12);

        assertEquals(
                "Clock moved backwards. Refusing to generate ID for 12 milliseconds",
                MessageContext.call(Locale.ENGLISH, exception::getMessage)
        );
        assertEquals(
                "检测到系统时钟回拨，将在 12 毫秒内拒绝生成 ID",
                MessageContext.call(Locale.SIMPLIFIED_CHINESE, exception::getMessage)
        );
    }
}
