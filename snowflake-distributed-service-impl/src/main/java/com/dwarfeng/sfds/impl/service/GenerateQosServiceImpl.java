package com.dwarfeng.sfds.impl.service;

import com.dwarfeng.sfds.stack.handler.GenerateHandler;
import com.dwarfeng.sfds.stack.service.GenerateQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateQosServiceImpl implements GenerateQosService {

    private final GenerateHandler generateHandler;

    private final ServiceExceptionMapper sem;

    public GenerateQosServiceImpl(GenerateHandler generateHandler, ServiceExceptionMapper sem) {
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
    public List<Long> generateLong(int size) throws ServiceException {
        try {
            return generateHandler.generateLong(size);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成 Long 时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
