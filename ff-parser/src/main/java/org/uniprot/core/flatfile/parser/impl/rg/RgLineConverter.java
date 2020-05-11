package org.uniprot.core.flatfile.parser.impl.rg;

import org.uniprot.core.flatfile.parser.Converter;

import java.util.ArrayList;
import java.util.List;

public class RgLineConverter implements Converter<RgLineObject, List<String>> {
    @Override
    public List<String> convert(RgLineObject f) {
        return new ArrayList<>(f.reference_groups);
    }
}
