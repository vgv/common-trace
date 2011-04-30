package me.vgv.common.trace.service;

import me.vgv.common.trace.TraceResult;

/**
 * Сервис, сохраняющий результаты трассировки во внешнем
 * хранилище (файл, база и т.д.)
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface TraceService {

	/**
	 * Сохраняет результаты трассировки во внешнем хранилище
	 *
	 * @param traceResult результаты трассировки
	 */
	void trace(TraceResult traceResult);

}
