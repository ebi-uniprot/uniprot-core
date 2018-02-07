package uk.ac.ebi.uniprot.validator;

import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The entry version's date must not before the integration date.
 */
public class DtVersionCheckValidator implements ConstraintValidator<DtDateCheck, DtLineObject> {


    @Override
    public void initialize(DtDateCheck specListCheck) {
    }

    @Override
    public boolean isValid(DtLineObject s, ConstraintValidatorContext context) {
        return s.seq_version <= s.entry_version;
    }
}
