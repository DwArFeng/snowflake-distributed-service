package com.dwarfeng.sfds.stack.internal.i18n;

import com.dwarfeng.sfds.base.sdk.i18n.Messages;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Stack 模块消息测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class StackMessagesTest {

    @Test
    public void shouldResolveEnglishAndSimplifiedChineseMessages() {
        assertEquals(
                "Clock moved backwards. Refusing to generate ID for 12 milliseconds",
                StackMessages.message(Locale.ENGLISH, StackMessageKey.CLOCK_MOVED_BACKWARDS_DETAIL, 12)
        );
        assertEquals(
                "检测到系统时钟回拨，将在 12 毫秒内拒绝生成 ID",
                StackMessages.message(Locale.SIMPLIFIED_CHINESE, StackMessageKey.CLOCK_MOVED_BACKWARDS_DETAIL, 12)
        );
    }

    @Test
    public void shouldFallbackToMissingKeyMarker() {
        assertEquals(
                "!missing.key!",
                Messages.resolve(
                        StackMessages.Catalog.STACK.messageCatalog(), "missing.key", Locale.ENGLISH
                )
        );
    }

    @Test
    public void shouldKeepKeysAlignedWithResources() throws IOException {
        Map<StackMessages.Catalog, Set<String>> keys = Arrays.stream(StackMessageKey.values()).collect(
                Collectors.groupingBy(
                        StackMessageKey::catalog, Collectors.mapping(StackMessageKey::key, Collectors.toSet())
                )
        );

        for (StackMessages.Catalog catalog : StackMessages.Catalog.values()) {
            Properties rootProperties = loadProperties(catalog, Locale.ROOT);
            Properties chineseProperties = loadProperties(catalog, Locale.SIMPLIFIED_CHINESE);
            assertEquals(keys.get(catalog), rootProperties.stringPropertyNames());
            assertEquals(rootProperties.stringPropertyNames(), chineseProperties.stringPropertyNames());
        }
    }

    @Test
    public void shouldCacheOneMessageCatalogPerCatalog() {
        Object first = StackMessages.Catalog.STACK.messageCatalog();
        Object second = StackMessages.Catalog.STACK.messageCatalog();
        assertSame(first, second);
    }

    private Properties loadProperties(StackMessages.Catalog catalog, Locale locale) throws IOException {
        String resourceName = catalog.messageCatalog().baseName().replace('.', '/');
        if (!Locale.ROOT.equals(locale)) {
            resourceName += "_" + locale;
        }
        resourceName += ".properties";

        try (InputStream inputStream = catalog.messageCatalog().module().getResourceAsStream(resourceName)) {
            assertNotNull(inputStream, resourceName);
            Properties properties = new Properties();
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            return properties;
        }
    }
}
