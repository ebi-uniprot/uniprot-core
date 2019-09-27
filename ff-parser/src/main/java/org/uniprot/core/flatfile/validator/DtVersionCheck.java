package org.uniprot.core.flatfile.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DtVersionCheckValidator.class)
@Documented
public @interface DtVersionCheck {

    String message() default "Sequence version can not be smaller than entry version.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
