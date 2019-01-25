package uk.ac.ebi.uniprot.parser.impl.ac;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtAccessionBuilder;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.List;
import java.util.stream.Collectors;

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
