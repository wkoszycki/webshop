package javax.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *
*
 * Work around I didn't wanted to include the entire EE 6 api! so a bit of tricky hacks :)
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagedBean {

    public String value() default "";
}
