package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * LongID服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface LongIdService extends Service {

    /**
     * 生成下一个LongID。
     *
     * @return 下一个LongID。
     * @throws ServiceException 服务异常。
     */
    long nextLongId() throws ServiceException;

    /**
     * 生成下一个LongID
     *
     * @return 下一个LongID
     * @throws ServiceException 服务异常。
     */
    LongIdKey nextLongIdKey() throws ServiceException;
}
