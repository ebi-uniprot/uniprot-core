package uk.ac.ebi.uniprot.flatfile.parser.impl.ac;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

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
