package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 解析 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
public interface ResolveQosService extends Service {

    /**
     * 解析 Long。
     *
     * @param id 待解析的 Long ID。
     * @return 解析结果。
     * @throws ServiceException 服务异常。
     */
    ResolveResult resolveLong(long id) throws ServiceException;
}

