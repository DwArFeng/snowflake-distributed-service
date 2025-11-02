package com.dwarfeng.sfds.stack.handler;

import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 解析处理器。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
public interface ResolveHandler extends Handler {

    /**
     * 解析 Long。
     *
     * @param id 待解析的 Long ID。
     * @return 解析结果。
     * @throws HandlerException 处理器异常。
     */
    ResolveResult resolveLong(long id) throws HandlerException;

    /**
     * 解析 LongIdKey。
     *
     * @param idKey 待解析的 LongIdKey。
     * @return 解析结果。
     * @throws HandlerException 处理器异常。
     */
    ResolveResult resolveLongIdKey(LongIdKey idKey) throws HandlerException;
}

