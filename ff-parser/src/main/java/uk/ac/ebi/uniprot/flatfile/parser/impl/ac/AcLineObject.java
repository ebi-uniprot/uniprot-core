package uk.ac.ebi.uniprot.flatfile.parser.impl.ac;

import uk.ac.ebi.uniprot.flatfile.validator.AlphanumericalOrderCheck;
import uk.ac.ebi.uniprot.flatfile.validator.DuplicatedAcCheck;

import java.util.ArrayList;
import java.util.List;

@DuplicatedAcCheck
public class AcLineObject {
    public String primaryAcc;

    @AlphanumericalOrderCheck(message = "Secondary Acc is not ordered alphanumerically, or contains duplication.")
    public List<String> secondaryAcc = new ArrayList<String>();
}
