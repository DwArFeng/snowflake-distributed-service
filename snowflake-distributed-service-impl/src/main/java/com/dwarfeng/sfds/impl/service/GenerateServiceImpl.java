package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.stack.handler.GenerateHandler;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateServiceImpl implements GenerateService {

    private final GenerateHandler generateHandler;

    private final ServiceExceptionMapper sem;

    public GenerateServiceImpl(GenerateHandler generateHandler, ServiceExceptionMapper sem) {
        this.generateHandler = generateHandler;
        this.sem = sem;
    }

    @Override
    public long generateLong() throws ServiceException {
        try {
            return generateHandler.generateLong();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成 Long 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey generateLongIdKey() throws ServiceException {
        try {
            return generateHandler.generateLongIdKey();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成 LongIdKey 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<Long> generateLong(int size) throws ServiceException {
        try {
            return generateHandler.generateLong(size);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成 Long 组时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LongIdKey> generateLongIdKey(int size) throws ServiceException {
        try {
            return generateHandler.generateLongIdKey(size);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成 LongIdKey 组时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
