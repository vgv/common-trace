package me.vgv.common.trace;

import me.vgv.common.trace.annotation.Trace;

/**
 * Специальный тестовый класс, помеченный аннотацией @Profile
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@Trace
class TestTraceClass {

	public Object get() {
		return new Object();
	}

}
