package org.uniprot.core.flatfile.parser.impl.id;

import java.util.AbstractMap;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;
import org.uniprot.core.uniprotkb.UniProtkbId;
import org.uniprot.core.uniprotkb.impl.UniProtkbIdBuilder;

public class IdLineConverter
        implements Converter<IdLineObject, Map.Entry<UniProtkbId, UniProtkbEntryType>> {

    @Override
    public Map.Entry<UniProtkbId, UniProtkbEntryType> convert(IdLineObject f) {
        UniProtkbId id = new UniProtkbIdBuilder(f.getEntryName()).build();
        UniProtkbEntryType type = UniProtkbEntryType.TREMBL;
        if (f.getReviewed()) {
            type = UniProtkbEntryType.SWISSPROT;
        }
        return new AbstractMap.SimpleEntry<>(id, type);
    }
}
