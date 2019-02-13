package uk.ac.ebi.uniprot.flatfile.parser.impl.sq;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;

public class SqLineConverter implements Converter<SqLineObject, Sequence> {
	@Override
	public Sequence convert(SqLineObject f) {
		return new SequenceBuilder(f.sequence).build();
	}
}
