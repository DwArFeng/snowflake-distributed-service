package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.sfds.stack.exception.ServiceException;

/**
 * GUID服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface GuidService extends Service {

    /**
     * 生成下一个GUID。
     *
     * @return 下一个GUID。
     * @throws ServiceException 服务异常。
     */
    long nextGuid() throws ServiceException;
}
