package me.vgv.common.trace;

import java.lang.reflect.Method;

/**
 * Результат трассировки - время, имя метода и признак успешного или провалившегося вызова
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TraceResult {

	private final long time;
	private final Method method;
	private final boolean success;

	public TraceResult(long time, Method method, boolean success) {
		this.time = time;
		this.method = method;
		this.success = success;
	}

	public long getTime() {
		return time;
	}

	public Method getMethod() {
		return method;
	}

	public boolean isSuccess() {
		return success;
	}
}
