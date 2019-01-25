package uk.ac.ebi.uniprot.parser.impl.id;


import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtIdBuilder;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.AbstractMap;
import java.util.Map;

public class IdLineConverter implements Converter<IdLineObject, Map.Entry<UniProtId, UniProtEntryType>> {

    @Override
    public Map.Entry<UniProtId, UniProtEntryType> convert(IdLineObject f) {
        UniProtId id = new UniProtIdBuilder(f.getEntryName()).build();
        UniProtEntryType type = UniProtEntryType.TREMBL;
        if (f.getReviewed()) {
            type = UniProtEntryType.SWISSPROT;
        }
        return new AbstractMap.SimpleEntry<>(id, type);
    }

}
