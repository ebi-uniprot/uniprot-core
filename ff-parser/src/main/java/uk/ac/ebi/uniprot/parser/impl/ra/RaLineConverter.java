package uk.ac.ebi.uniprot.parser.impl.ra;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.parser.Converter;

public class RaLineConverter implements Converter<RaLineObject, List<Author> > {

	@Override
	public List<Author> convert(RaLineObject f) {
		return f.authors.stream().map(val -> AbstractCitationBuilder.createAuthor(val))
				.collect(Collectors.toList());
		
	}

}
