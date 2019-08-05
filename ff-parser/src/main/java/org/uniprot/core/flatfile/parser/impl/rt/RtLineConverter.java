package org.uniprot.core.flatfile.parser.impl.rt;

import org.uniprot.core.flatfile.parser.Converter;

public class RtLineConverter implements Converter<RtLineObject, String> {
    @Override
    public String convert(RtLineObject f) {
        return f.title;
    }
}
