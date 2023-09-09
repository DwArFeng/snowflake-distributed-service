package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 生成服务。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public interface GenerateService extends Service {

    /**
     * 生成 Long。
     *
     * @return 生成的 Long。
     * @throws ServiceException 服务异常。
     */
    long generateLong() throws ServiceException;

    /**
     * 生成 LongIdKey。
     *
     * @return 生成的 LongIdKey。
     * @throws ServiceException 服务异常。
     */
    LongIdKey generateLongIdKey() throws ServiceException;

    /**
     * 生成 Long。
     *
     * @param size 生成的数量。
     * @return 生成的 Long 组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<Long> generateLong(int size) throws ServiceException;

    /**
     * 生成 LongIdKey。
     *
     * @param size 生成的数量。
     * @return 生成的 LongIdKey 组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<LongIdKey> generateLongIdKey(int size) throws ServiceException;
}
