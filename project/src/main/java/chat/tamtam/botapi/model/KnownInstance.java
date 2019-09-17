package chat.tamtam.botapi.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import chat.tamtam.botapi.model.validation.KnownInstanceValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author alexandrchuprin
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {KnownInstanceValidator.class})
@Documented
public @interface KnownInstance {
    String discriminator();

    Class<?> ofClass();

    String message() default "{chat.tamtam.botapi.KnownInstance.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
