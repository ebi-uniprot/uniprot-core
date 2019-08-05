package org.uniprot.core.flatfile.parser.impl.id;


import java.util.AbstractMap;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;

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
