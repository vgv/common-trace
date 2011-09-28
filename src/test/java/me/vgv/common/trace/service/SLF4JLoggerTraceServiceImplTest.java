package me.vgv.common.trace.service;

import me.vgv.common.trace.TraceResult;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.slf4j.Logger;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SLF4JLoggerTraceServiceImplTest {

	@Test
	public void testTrace() throws Exception {
		Logger log = Mockito.mock(Logger.class);
		Mockito.when(log.isInfoEnabled()).thenReturn(true);

		SLF4JLoggerTraceServiceImpl slf4JLoggerProfileService = new SLF4JLoggerTraceServiceImpl();
		slf4JLoggerProfileService.log = log;
		TraceResult traceResult = new TraceResult(123, Object.class.getDeclaredMethod("hashCode"), true);

		slf4JLoggerProfileService.trace(traceResult);

		Mockito.verify(log, VerificationModeFactory.times(1)).info("trace:java.lang.Object:hashCode:ok:123");
	}
}
