package uk.ac.ebi.uniprot.flatfile.parser.impl.rt;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

public class RtLineConverter implements Converter<RtLineObject, String> {
    @Override
    public String convert(RtLineObject f) {
        return f.title;
    }
}
