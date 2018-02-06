package uk.ac.ebi.uniprot.validator.dt;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DtVersionCheckValidator.class)
@Documented
public @interface DtVersionCheck {

    String message() default "Sequence version can not be smaller than entry version.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
