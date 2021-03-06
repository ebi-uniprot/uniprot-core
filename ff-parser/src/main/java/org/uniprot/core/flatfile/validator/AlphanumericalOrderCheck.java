package org.uniprot.core.flatfile.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphanumericalOrderCheckValidator.class)
@Documented
public @interface AlphanumericalOrderCheck {

    String message() default "Sequence Length in ID Line should match its value in Sequence Line";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Order order() default Order.ASC;

    public static enum Order {
        ASC,
        DESC;
    }
}
