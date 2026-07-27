package com.dwarfeng.sfds.stack.internal.i18n;

import static com.dwarfeng.sfds.stack.internal.i18n.StackMessages.Catalog.STACK;

/**
 * Stack 模块消息键。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public enum StackMessageKey {

    CLOCK_MOVED_BACKWARDS_DETAIL(STACK, "exception.clock_moved_backwards_detail");

    private final StackMessages.Catalog catalog;
    private final String key;

    StackMessageKey(StackMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    StackMessages.Catalog catalog() {
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
