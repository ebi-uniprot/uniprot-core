package uk.ac.ebi.uniprot.parser.impl.id;


import java.util.AbstractMap;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class IdLineConverter implements Converter<IdLineObject, Map.Entry<UniProtId, UniProtEntryType>> {

	@Override
	public Map.Entry<UniProtId, UniProtEntryType> convert(IdLineObject f) {
		UniProtId id = UniProtFactory.INSTANCE.createUniProtId(f.entryName);
		UniProtEntryType type = UniProtEntryType.TREMBL;
		if(f.reviewed){
			type =UniProtEntryType.SWISSPROT;
		}
		return new AbstractMap.SimpleEntry<>(id, type);
	}

}
