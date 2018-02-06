package uk.ac.ebi.uniprot.parser.impl.ac;

import uk.ac.ebi.uniprot.validator.ac.DuplicatedAcCheck;
import uk.ac.ebi.uniprot.validator.common.AlphanumericalOrderCheck;

import java.util.ArrayList;
import java.util.List;

@DuplicatedAcCheck
public class AcLineObject {
    public String primaryAcc;

    @AlphanumericalOrderCheck(message = "Secondary Acc is not ordered alphanumerically, or contains duplication.")
    public List<String> secondaryAcc = new ArrayList<String>();
}
