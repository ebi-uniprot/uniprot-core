package org.uniprot.core.flatfile.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DtDateCheckValidator.class)
@Documented
public @interface DtDateCheck {

    String message() default "Entry's date can not be any earlier than the integration date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
