package uk.ac.ebi.uniprot.flatfile.validator;

import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The entry version's date must not before the integration date.
 */
public class DtDateCheckValidator implements ConstraintValidator<DtDateCheck, DtLineObject> {


    @Override
    public void initialize(DtDateCheck specListCheck) {
    }

    @Override
    public boolean isValid(DtLineObject s, ConstraintValidatorContext context) {
       return !s.entry_date.isBefore(s.integration_date);
    }
}
