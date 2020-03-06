package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionBuilder;

public class AcLineConverter implements Converter<AcLineObject, UniProtAcLineObject> {

    @Override
    public UniProtAcLineObject convert(AcLineObject f) {
        UniProtAccession primaryAcc = new UniProtAccessionBuilder(f.primaryAcc).build();
        List<UniProtAccession> secondAccessions =
                f.secondaryAcc.stream()
                        .map(val -> new UniProtAccessionBuilder(val).build())
                        .collect(Collectors.toList());
        return new UniProtAcLineObject(primaryAcc, secondAccessions);
    }
}
