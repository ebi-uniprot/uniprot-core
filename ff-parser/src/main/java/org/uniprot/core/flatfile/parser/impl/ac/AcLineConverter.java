package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

public class AcLineConverter implements Converter<AcLineObject, UniProtKBAcLineObject> {

    @Override
    public UniProtKBAcLineObject convert(AcLineObject f) {
        UniProtKBAccession primaryAcc = new UniProtKBAccessionBuilder(f.primaryAcc).build();
        List<UniProtKBAccession> secondAccessions =
                f.secondaryAcc.stream()
                        .map(val -> new UniProtKBAccessionBuilder(val).build())
                        .collect(Collectors.toList());
        return new UniProtKBAcLineObject(primaryAcc, secondAccessions);
    }
}
