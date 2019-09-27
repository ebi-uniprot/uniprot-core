package org.uniprot.core.flatfile.parser.impl.ra;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.builder.AuthorBuilder;
import org.uniprot.core.flatfile.parser.Converter;

public class RaLineConverter implements Converter<RaLineObject, List<Author>> {

    @Override
    public List<Author> convert(RaLineObject f) {
        return f.authors.stream()
                .map(val -> new AuthorBuilder(val).build())
                .collect(Collectors.toList());
    }
}
