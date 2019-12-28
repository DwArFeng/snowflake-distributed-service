package com.dwarfeng.sfds.api;

import com.dwarfeng.sfds.stack.exception.ServiceException;
import com.dwarfeng.sfds.stack.service.Service;

public interface GuidApi extends Service {

    /**
     * 生成下一个GUID。
     *
     * @return 下一个GUID。
     * @throws ServiceException 服务异常。
     */
    long nextGuid() throws ServiceException;
}
