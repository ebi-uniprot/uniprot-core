package org.uniprot.core.flatfile.parser.impl.sq;

import org.uniprot.core.Sequence;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.impl.SequenceBuilder;

public class SqLineConverter implements Converter<SqLineObject, Sequence> {
    @Override
    public Sequence convert(SqLineObject f) {
        return new SequenceBuilder(f.sequence).build();
    }
}
