package com.dwarfeng.sfds.sdk.exception;

import com.dwarfeng.sfds.stack.exception.ClockMovedBackwardsException;
import com.dwarfeng.subgrade.basic.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 服务异常帮助类测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ServiceExceptionHelperTest {

    @Test
    public void shouldProvideSuppliersForDirectParentAndDefaultRoutes() {
        int originalOffset = ServiceExceptionCodeSuppliers.getExceptionCodeOffset();
        try {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1500);
            Map<Class<? extends Exception>, Supplier<ServiceException.Code>> destination =
                    ServiceExceptionHelper.putDefaultDestination(null);
            AtomicInteger defaultSequence = new AtomicInteger();
            MapServiceExceptionMapper mapper = new MapServiceExceptionMapper(
                    destination,
                    () -> new ServiceException.Code(9000 + defaultSequence.incrementAndGet(), "undefined")
            );

            assertEquals(1500, mapper.map(new ClockMovedBackwardsException(1)).getCode().getCode());
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1600);
            assertEquals(1600, mapper.map(new ClockMovedBackwardsException(1)).getCode().getCode());

            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1700);
            assertEquals(1700, mapper.map(new ExtendedClockMovedBackwardsException(1)).getCode().getCode());
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1800);
            assertEquals(1800, mapper.map(new ExtendedClockMovedBackwardsException(1)).getCode().getCode());

            assertEquals(9001, mapper.map(new IllegalStateException()).getCode().getCode());
            assertEquals(9002, mapper.map(new IllegalStateException()).getCode().getCode());
        } finally {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(originalOffset);
        }
    }

    private static class ExtendedClockMovedBackwardsException extends ClockMovedBackwardsException {

        @Serial
        private static final long serialVersionUID = 4046814429123771800L;

        private ExtendedClockMovedBackwardsException(long duration) {
            super(duration);
        }
    }
}
