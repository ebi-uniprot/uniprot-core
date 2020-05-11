package org.uniprot.core.flatfile.parser.impl.ac;

import org.uniprot.core.flatfile.validator.AlphanumericalOrderCheck;
import org.uniprot.core.flatfile.validator.DuplicatedAcCheck;

import java.util.ArrayList;
import java.util.List;

@DuplicatedAcCheck
public class AcLineObject {
    public String primaryAcc;

    @AlphanumericalOrderCheck(
            message = "Secondary Acc is not ordered alphanumerically, or contains duplication.")
    public List<String> secondaryAcc = new ArrayList<String>();
}
