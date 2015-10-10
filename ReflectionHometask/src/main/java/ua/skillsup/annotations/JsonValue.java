package ua.skillsup.annotations;
/**
 * Created by Honey on 16.09.2015.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)

public @interface JsonValue {
    public String name();
}
