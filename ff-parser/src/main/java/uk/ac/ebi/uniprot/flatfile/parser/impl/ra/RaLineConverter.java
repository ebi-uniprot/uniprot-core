package uk.ac.ebi.uniprot.flatfile.parser.impl.ra;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.builder.AuthorBuilder;

public class RaLineConverter implements Converter<RaLineObject, List<Author>> {

    @Override
    public List<Author> convert(RaLineObject f) {
        return f.authors.stream()
                .map(val -> new AuthorBuilder(val).build())
                .collect(Collectors.toList());
    }

}
