package me.vgv.common.trace;

import me.vgv.common.trace.annotation.NoTrace;
import me.vgv.common.trace.annotation.Trace;
import me.vgv.common.trace.service.TraceService;
import org.aopalliance.intercept.MethodInvocation;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class TraceInterceptorTest {

	@Test
	public void testTraceMethodWithoutAnnotation() throws Throwable {
		MethodInvocation methodInvocation = Mockito.mock(MethodInvocation.class);
		Object expectedResult = new Object();
		Mockito.when(methodInvocation.proceed()).thenReturn(expectedResult);
		Mockito.when(methodInvocation.getThis()).thenReturn(expectedResult); // что угодно, что не помечено @Trace

		TraceService traceService = Mockito.mock(TraceService.class);

		TraceInterceptor traceInterceptor = new TraceInterceptor();
		traceInterceptor.setTraceService(traceService);

		Object result = traceInterceptor.invoke(methodInvocation);

		// проверим что вернули что надо
		Assert.assertEquals(result, expectedResult);
		// и что вызова к TraceService не было
		Mockito.verifyZeroInteractions(traceService);
	}

	@Test
	public void testTraceMethodIfNoErrors() throws Throwable {
		Object expectedResult = new Object();
		MethodInvocation methodInvocation = Mockito.mock(MethodInvocation.class);
		Mockito.when(methodInvocation.getThis()).thenReturn(new TestTraceClass());
		Mockito.when(methodInvocation.getMethod()).thenReturn(TestTraceClass.class.getDeclaredMethod("get"));
		Mockito.when(methodInvocation.proceed()).thenReturn(expectedResult);

		TraceService traceService = Mockito.mock(TraceService.class);

		TraceInterceptor traceInterceptor = new TraceInterceptor();
		traceInterceptor.setTraceService(traceService);

		Object result = traceInterceptor.invoke(methodInvocation);

		// проверим что вернули что надо
		Assert.assertEquals(result, expectedResult);
		// и что вызов к TraceService был
		Mockito.verify(traceService, VerificationModeFactory.times(1)).trace(Mockito.<TraceResult>any());
	}

	@Test
	public void testTraceMethodWithNoTraceAnnotation() throws Throwable {
		Object expectedResult = new Object();
		MethodInvocation methodInvocation = Mockito.mock(MethodInvocation.class);
		Mockito.when(methodInvocation.getThis()).thenReturn(new TestNoTraceClass());
		Mockito.when(methodInvocation.getMethod()).thenReturn(TestNoTraceClass.class.getDeclaredMethod("get"));
		Mockito.when(methodInvocation.proceed()).thenReturn(expectedResult);

		TraceService traceService = Mockito.mock(TraceService.class);

		TraceInterceptor traceInterceptor = new TraceInterceptor();
		traceInterceptor.setTraceService(traceService);

		Object result = traceInterceptor.invoke(methodInvocation);

		// проверим что вернули что надо
		Assert.assertEquals(result, expectedResult);
		// и что вызова к TraceService не было
		Mockito.verifyZeroInteractions(traceService);
	}
}

/**
 * Специальный тестовый класс, помеченный аннотацией @Trace с методом, который @NoTrace
 */
@Trace
class TestNoTraceClass {

	@NoTrace
	public Object get() {
		return new Object();
	}

}