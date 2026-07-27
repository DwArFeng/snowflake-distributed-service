package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.stack.handler.GenerateHandler;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.basic.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.basic.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
public class LongIdServiceImpl implements LongIdService {

    private final GenerateHandler generateHandler;

    private final ServiceExceptionMapper sem;

    public LongIdServiceImpl(GenerateHandler generateHandler, ServiceExceptionMapper sem) {
        this.generateHandler = generateHandler;
        this.sem = sem;
    }

    @Override
    public long nextLongId() throws ServiceException {
        try {
            return generateHandler.generateLong();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成下一个 Long ID 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey nextLongIdKey() throws ServiceException {
        try {
            return generateHandler.generateLongIdKey();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成下一个 LongIdKey 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<Long> nextLongId(int size) throws ServiceException {
        try {
            return generateHandler.generateLong(size);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成下一组 Long ID 时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LongIdKey> nextLongIdKey(int number) throws ServiceException {
        try {
            return generateHandler.generateLongIdKey(number);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成下一组 LongIdKey 时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
