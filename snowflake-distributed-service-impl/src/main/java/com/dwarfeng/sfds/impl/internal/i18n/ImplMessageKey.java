package com.dwarfeng.sfds.impl.internal.i18n;

import static com.dwarfeng.sfds.impl.internal.i18n.ImplMessages.Catalog.IMPL;

/**
 * Impl 模块消息键。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public enum ImplMessageKey {

    GENERATE_HANDLER_WORKER_ID_INVALID_LOG(IMPL, "generate_handler.worker_id_invalid_log"),
    GENERATE_HANDLER_WORKER_ID_INVALID_DETAIL(IMPL, "generate_handler.worker_id_invalid_detail"),
    GENERATE_HANDLER_DATACENTER_ID_INVALID_LOG(IMPL, "generate_handler.datacenter_id_invalid_log"),
    GENERATE_HANDLER_DATACENTER_ID_INVALID_DETAIL(IMPL, "generate_handler.datacenter_id_invalid_detail"),
    GENERATE_HANDLER_CLOCK_MOVED_BACKWARDS_LOG(IMPL, "generate_handler.clock_moved_backwards_log"),
    RESOLVE_HANDLER_INITIALIZED(IMPL, "resolve_handler.initialized"),
    TELQOS_INTERNAL_UNREACHABLE(IMPL, "telqos.internal.unreachable"),
    TELQOS_GENERATE_DESCRIPTION(IMPL, "telqos.generate.description"),
    TELQOS_GENERATE_OPTION_GENERATE(IMPL, "telqos.generate.option.generate"),
    TELQOS_GENERATE_OPTION_SIZE(IMPL, "telqos.generate.option.size"),
    TELQOS_GENERATE_OPTION_FILE(IMPL, "telqos.generate.option.file"),
    TELQOS_GENERATE_PROMPT_SIZE(IMPL, "telqos.generate.prompt.size"),
    TELQOS_GENERATE_PROMPT_OVERWRITE(IMPL, "telqos.generate.prompt.overwrite"),
    TELQOS_GENERATE_OPERATION_CANCELLED(IMPL, "telqos.generate.operation.cancelled"),
    TELQOS_GENERATE_EXPORT_SUCCEEDED(IMPL, "telqos.generate.export.succeeded"),
    TELQOS_RESOLVE_DESCRIPTION(IMPL, "telqos.resolve.description"),
    TELQOS_RESOLVE_OPTION_RESOLVE(IMPL, "telqos.resolve.option.resolve"),
    TELQOS_RESOLVE_OPTION_ID(IMPL, "telqos.resolve.option.id"),
    TELQOS_RESOLVE_PROMPT_ID(IMPL, "telqos.resolve.prompt.id"),
    TELQOS_RESOLVE_RESULT_NULL(IMPL, "telqos.resolve.result.null"),
    TELQOS_RESOLVE_RESULT_HEADER(IMPL, "telqos.resolve.result.header"),
    TELQOS_RESOLVE_RESULT_ORIGINAL_ID(IMPL, "telqos.resolve.result.original_id"),
    TELQOS_RESOLVE_RESULT_SEQUENCE(IMPL, "telqos.resolve.result.sequence"),
    TELQOS_RESOLVE_RESULT_WORKER_ID(IMPL, "telqos.resolve.result.worker_id"),
    TELQOS_RESOLVE_RESULT_DATACENTER_ID(IMPL, "telqos.resolve.result.datacenter_id"),
    TELQOS_RESOLVE_RESULT_TIMESTAMP_DELTA(IMPL, "telqos.resolve.result.timestamp_delta"),
    TELQOS_RESOLVE_RESULT_TIMESTAMP(IMPL, "telqos.resolve.result.timestamp"),
    TELQOS_RESOLVE_RESULT_TWEPOCH(IMPL, "telqos.resolve.result.twepoch");

    private final ImplMessages.Catalog catalog;
    private final String key;

    ImplMessageKey(ImplMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    ImplMessages.Catalog catalog() {
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
