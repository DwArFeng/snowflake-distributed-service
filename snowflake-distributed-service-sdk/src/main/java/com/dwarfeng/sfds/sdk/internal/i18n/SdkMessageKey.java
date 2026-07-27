package com.dwarfeng.sfds.sdk.internal.i18n;

import static com.dwarfeng.sfds.sdk.internal.i18n.SdkMessages.Catalog.SDK;

/**
 * SDK 模块消息键。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public enum SdkMessageKey {

    SERVICE_EXCEPTION_CLOCK_MOVED_BACKWARDS(SDK, "service_exception.clock_moved_backwards");

    private final SdkMessages.Catalog catalog;
    private final String key;

    SdkMessageKey(SdkMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    SdkMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 获取消息键。
     *
     * @return 消息键。
     */
    public String key() {
        return key;
    }
}
