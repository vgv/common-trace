package me.vgv.common.trace;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import me.vgv.common.trace.annotation.Trace;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class TraceModule extends AbstractModule {

	@Override
	protected void configure() {
		// profile interceptor
		TraceInterceptor traceInterceptor = new TraceInterceptor();
		requestInjection(traceInterceptor);
		bindInterceptor(Matchers.annotatedWith(Trace.class), Matchers.any(), traceInterceptor);
	}

}
