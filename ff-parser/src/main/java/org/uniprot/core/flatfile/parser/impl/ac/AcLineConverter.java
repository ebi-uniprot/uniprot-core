package org.uniprot.core.flatfile.parser.impl.ac;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

public class AcLineConverter implements Converter<AcLineObject, UniProtkbAcLineObject> {

    @Override
    public UniProtkbAcLineObject convert(AcLineObject f) {
        UniProtkbAccession primaryAcc = new UniProtkbAccessionBuilder(f.primaryAcc).build();
        List<UniProtkbAccession> secondAccessions =
                f.secondaryAcc.stream()
                        .map(val -> new UniProtkbAccessionBuilder(val).build())
                        .collect(Collectors.toList());
        return new UniProtkbAcLineObject(primaryAcc, secondAccessions);
    }
}
