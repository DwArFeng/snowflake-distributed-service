package com.dwarfeng.sfds.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * Long ID 服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 * @deprecated 由于命名不合理，该接口已经被 {@link GenerateService} 取代。
 */
/*
 * 由于其它项目中仍在在使用该服务，所以需要使用该服务。
 *   仍在使用该服务的文件：
 *     - node 模块 spring/application-context-dubbo.xml。
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public interface LongIdService extends Service {

    /**
     * 生成下一个 Long ID。
     *
     * @return 下一个 Long ID。
     * @throws ServiceException 服务异常。
     */
    long nextLongId() throws ServiceException;

    /**
     * 生成下一个 LongIdKey。
     *
     * @return 下一个 LongIdKey。
     * @throws ServiceException 服务异常。
     */
    LongIdKey nextLongIdKey() throws ServiceException;

    /**
     * 生成下一组 Long ID。
     *
     * @param size 生成 ID 的数量。
     * @return 下一个LongID。
     * @throws ServiceException 服务异常。
     * @since 1.4.7
     */
    List<Long> nextLongId(int size) throws ServiceException;

    /**
     * 生成下一组 LongIdKey。
     *
     * @param size 生成 ID 的数量。
     * @return 下一组 LongIdKey
     * @throws ServiceException 服务异常。
     * @since 1.4.7
     */
    List<LongIdKey> nextLongIdKey(int size) throws ServiceException;
}
