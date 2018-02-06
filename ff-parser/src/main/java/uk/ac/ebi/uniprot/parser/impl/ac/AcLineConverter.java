package uk.ac.ebi.uniprot.parser.impl.ac;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class AcLineConverter implements Converter<AcLineObject, UniProtAcLineObject> {

	@Override
	public UniProtAcLineObject convert(AcLineObject f) {
		UniProtAccession primaryAcc = UniProtFactory.INSTANCE.createUniProtAccession(f.primaryAcc);
		List<UniProtAccession> secondAccessions = 
				f.secondaryAcc.stream().map(val ->UniProtFactory.INSTANCE.createUniProtAccession(val) )
				.collect(Collectors.toList());
		return new UniProtAcLineObject(primaryAcc, secondAccessions);	
	}

}
