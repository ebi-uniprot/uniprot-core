package org.uniprot.core.flatfile.parser.impl.id;

import java.util.AbstractMap;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;

public class IdLineConverter
        implements Converter<IdLineObject, Map.Entry<UniProtKBId, UniProtKBEntryType>> {

    @Override
    public Map.Entry<UniProtKBId, UniProtKBEntryType> convert(IdLineObject f) {
        UniProtKBId id = new UniProtKBIdBuilder(f.getEntryName()).build();
        UniProtKBEntryType type = UniProtKBEntryType.TREMBL;
        if (f.getReviewed()) {
            type = UniProtKBEntryType.SWISSPROT;
        }
        return new AbstractMap.SimpleEntry<>(id, type);
    }
}
