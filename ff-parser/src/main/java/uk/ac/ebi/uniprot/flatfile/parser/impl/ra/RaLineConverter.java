package uk.ac.ebi.uniprot.flatfile.parser.impl.ra;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.builder.AuthorBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class RaLineConverter implements Converter<RaLineObject, List<Author>> {

    @Override
    public List<Author> convert(RaLineObject f) {
        return f.authors.stream()
                .map(val -> new AuthorBuilder(val).build())
                .collect(Collectors.toList());
    }

}
