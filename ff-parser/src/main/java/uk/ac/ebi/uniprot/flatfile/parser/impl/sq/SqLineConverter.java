package uk.ac.ebi.uniprot.flatfile.parser.impl.sq;

import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

public class SqLineConverter implements Converter<SqLineObject, Sequence> {
	@Override
	public Sequence convert(SqLineObject f) {
		return new SequenceBuilder(f.sequence).build();
	}
}
