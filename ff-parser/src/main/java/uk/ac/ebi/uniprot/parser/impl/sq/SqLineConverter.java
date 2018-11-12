package uk.ac.ebi.uniprot.parser.impl.sq;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class SqLineConverter implements Converter<SqLineObject, Sequence> {
	@Override
	public Sequence convert(SqLineObject f) {
		return UniProtFactory.INSTANCE.createSequence(f.sequence);
		
	}

}
