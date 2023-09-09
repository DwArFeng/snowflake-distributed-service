package com.dwarfeng.sfds.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * Long ID 处理器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface GenerateHandler extends Handler {

    /**
     * 生成 Long。
     *
     * @return 生成的 Long。
     * @throws HandlerException 处理器异常。
     */
    long generateLong() throws HandlerException;

    /**
     * 生成 LongIdKey。
     *
     * @return 生成的 LongIdKey。
     * @throws HandlerException 处理器异常。
     */
    LongIdKey generateLongIdKey() throws HandlerException;

    /**
     * 生成 Long。
     *
     * @param size 生成的数量。
     * @return 生成的 Long 组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<Long> generateLong(int size) throws HandlerException;

    /**
     * 生成 LongIdKey。
     *
     * @param size 生成的数量。
     * @return 生成的 LongIdKey 组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LongIdKey> generateLongIdKey(int size) throws HandlerException;
}
