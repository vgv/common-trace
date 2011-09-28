package me.vgv.common.trace.service;

import me.vgv.common.trace.TraceResult;
import me.vgv.common.utils.aop.AopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сохраняет трассировочные сообщения в логе с уровнем info
 * <p/>
 * Соообщения имеют такой формат:
 * <pre>trace:класс:метод:результат:продолжительность</pre>
 * <ul>
 * <li>Класс - название класса, если этот класс Guice-прокси - название базового класса
 * <li>Метод - название метода
 * <li>Результат - метод завершился нормально (значение 'ok') или бросил исключение (значение 'error')
 * <li>Продолжительность - продолжительность выполнения метода в миллисекундах
 * </ul>
 * <p/>
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class SLF4JLoggerTraceServiceImpl implements TraceService {

	static Logger log = LoggerFactory.getLogger(SLF4JLoggerTraceServiceImpl.class);

	@Override
	public void trace(TraceResult traceResult) {
		// если надо это логать - логаем
		if (log.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder(256);
			sb.append("trace:");
			sb.append(AopUtils.getTargetClass(traceResult.getMethod().getDeclaringClass()).getName());
			sb.append(":");
			sb.append(traceResult.getMethod().getName());
			sb.append(":");
			sb.append(traceResult.isSuccess() ? "ok" : "error");
			sb.append(":");
			sb.append(traceResult.getTime());

			log.info(sb.toString());
		}
	}

}
