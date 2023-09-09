package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * Long ID QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GenerateQosService extends Service {

    /**
     * 生成 Long。
     *
     * @return 生成的 Long。
     * @throws ServiceException 服务异常。
     */
    long generateLong() throws ServiceException;

    /**
     * 生成 Long。
     *
     * @param size 生成的数量。
     * @return 生成的 Long 组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<Long> generateLong(int size) throws ServiceException;
}
