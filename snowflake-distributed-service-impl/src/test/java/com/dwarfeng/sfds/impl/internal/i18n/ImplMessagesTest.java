package com.dwarfeng.sfds.impl.internal.i18n;

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
 * Impl 模块消息测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ImplMessagesTest {

    @Test
    public void shouldResolveEnglishAndSimplifiedChineseMessages() {
        assertEquals(
                "Generate IDs and export them to CSV",
                ImplMessages.message(Locale.ENGLISH, ImplMessageKey.TELQOS_GENERATE_DESCRIPTION)
        );
        assertEquals(
                "生成 ID 并导出到 CSV",
                ImplMessages.message(Locale.SIMPLIFIED_CHINESE, ImplMessageKey.TELQOS_GENERATE_DESCRIPTION)
        );
    }

    @Test
    public void shouldFallbackToMissingKeyMarker() {
        assertEquals(
                "!missing.key!",
                Messages.resolve(ImplMessages.Catalog.IMPL.messageCatalog(), "missing.key", Locale.ENGLISH)
        );
    }

    @Test
    public void shouldKeepKeysAlignedWithResources() throws IOException {
        Map<ImplMessages.Catalog, Set<String>> keys = Arrays.stream(ImplMessageKey.values()).collect(
                Collectors.groupingBy(
                        ImplMessageKey::catalog, Collectors.mapping(ImplMessageKey::key, Collectors.toSet())
                )
        );

        for (ImplMessages.Catalog catalog : ImplMessages.Catalog.values()) {
            Properties rootProperties = loadProperties(catalog, Locale.ROOT);
            Properties chineseProperties = loadProperties(catalog, Locale.SIMPLIFIED_CHINESE);
            assertEquals(keys.get(catalog), rootProperties.stringPropertyNames());
            assertEquals(rootProperties.stringPropertyNames(), chineseProperties.stringPropertyNames());
        }
    }

    @Test
    public void shouldCacheOneMessageCatalogPerCatalog() {
        Object first = ImplMessages.Catalog.IMPL.messageCatalog();
        Object second = ImplMessages.Catalog.IMPL.messageCatalog();
        assertSame(first, second);
    }

    private Properties loadProperties(ImplMessages.Catalog catalog, Locale locale) throws IOException {
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
