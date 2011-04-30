package me.vgv.common.trace;

import com.google.inject.Inject;
import me.vgv.common.trace.annotation.NoTrace;
import me.vgv.common.trace.annotation.Trace;
import me.vgv.common.trace.service.TraceService;
import me.vgv.common.utils.aop.AopUtils;
import me.vgv.common.utils.aop.InvocationResult;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Трассировочный интерцептор
 * <p/>
 * Формирует трассировочные сообщения и отдает их сервису ответственному за сохранение
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class TraceInterceptor implements MethodInterceptor {

	private TraceService traceService;

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		boolean traceNeeded = false;

		Class<?> clazz = methodInvocation.getThis().getClass(); // это может быть Guice-прокси, но у нас Trace - Inherited
		if (clazz.isAnnotationPresent(Trace.class)) {
			Method method = methodInvocation.getMethod();
			if (!method.isAnnotationPresent(NoTrace.class)) {
				// да, трассируем - класс помечен аннотацией @Trace, метод не помечен как @NoTrace
				traceNeeded = true;
			}
		}

		if (traceNeeded) {
			// сколько времени сейчас?
			long start = System.currentTimeMillis();
			InvocationResult invocationResult = AopUtils.invokeMethod(methodInvocation);
			// как долго выполнялся метод?
			start = System.currentTimeMillis() - start;

			TraceResult traceResult = new TraceResult(start, methodInvocation.getMethod(), invocationResult.getError() == null);
			traceService.trace(traceResult);

			// ну и наконец - вернем результат выполнения метода
			return AopUtils.processInvocationResult(invocationResult);
		} else {
			// класс не трассируемый - просто вызовем целевой метод
			return methodInvocation.proceed();
		}
	}

	@Inject
	public void setTraceService(TraceService traceService) {
		this.traceService = traceService;
	}
}
