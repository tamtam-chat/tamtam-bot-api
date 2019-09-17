package chat.tamtam.botapi.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import chat.tamtam.botapi.model.KnownInstance;

/**
 * @author alexandrchuprin
 */
public class KnownInstanceValidator implements ConstraintValidator<KnownInstance, Object> {
    private Class<?> clazz;
    private String discriminator;

    @Override
    public void initialize(KnownInstance constraintAnnotation) {
        clazz = constraintAnnotation.ofClass();
        discriminator = constraintAnnotation.discriminator();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.getClass() != clazz;
    }
}
