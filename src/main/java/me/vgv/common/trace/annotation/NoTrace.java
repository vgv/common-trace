package me.vgv.common.trace.annotation;

import java.lang.annotation.*;

/**
 * Методы, помеченные этой аннотацией не трассируются
 * <p/>
 * Дополнительно см. {@link Trace}
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NoTrace {

}
