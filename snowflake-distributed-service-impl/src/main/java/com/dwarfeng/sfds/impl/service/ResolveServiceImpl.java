package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.sfds.stack.handler.ResolveHandler;
import com.dwarfeng.sfds.stack.service.ResolveService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class ResolveServiceImpl implements ResolveService {

    private final ResolveHandler resolveHandler;

    private final ServiceExceptionMapper sem;

    public ResolveServiceImpl(ResolveHandler resolveHandler, ServiceExceptionMapper sem) {
        this.resolveHandler = resolveHandler;
        this.sem = sem;
    }

    @Override
    public ResolveResult resolveLong(long id) throws ServiceException {
        try {
            return resolveHandler.resolveLong(id);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("解析 Long 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ResolveResult resolveLongIdKey(LongIdKey idKey) throws ServiceException {
        try {
            return resolveHandler.resolveLongIdKey(idKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("解析 LongIdKey 时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

