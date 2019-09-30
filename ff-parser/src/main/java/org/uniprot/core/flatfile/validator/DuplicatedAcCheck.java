package org.uniprot.core.flatfile.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicatedAcCheckValidator.class)
@Documented
public @interface DuplicatedAcCheck {

    String message() default "The primary sccession is in the secondary accession list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
