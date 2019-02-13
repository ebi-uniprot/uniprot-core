package uk.ac.ebi.uniprot.validator;

import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 Species code in Id line should be in speclist.txt
 */
public class DuplicatedAcCheckValidator implements ConstraintValidator<DuplicatedAcCheck, AcLineObject> {


    @Override
    public void initialize(DuplicatedAcCheck specListCheck) {
    }

    @Override
    public boolean isValid(AcLineObject s, ConstraintValidatorContext context) {
        return !s.secondaryAcc.contains(s.primaryAcc);
    }
}
