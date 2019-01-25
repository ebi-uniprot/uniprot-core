package uk.ac.ebi.uniprot.parser.impl.rp;

import uk.ac.ebi.uniprot.parser.Converter;

import java.util.ArrayList;
import java.util.List;

public class RpLineConverter implements Converter<RpLineObject, List<String>> {
    @Override
    public List<String> convert(RpLineObject f) {
        return new ArrayList<>(f.scopes);
    }
}
