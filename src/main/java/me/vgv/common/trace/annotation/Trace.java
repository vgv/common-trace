package me.vgv.common.trace.annotation;

import java.lang.annotation.*;

/**
 * Классы, помеченные этой аннотацией и их наследники трассируются
 * <p/>
 * Дополнительно см. {@link NoTrace}
 * <p/>
 * Дополнительно см. {@link me.vgv.common.trace.TraceInterceptor}
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Trace {

}
