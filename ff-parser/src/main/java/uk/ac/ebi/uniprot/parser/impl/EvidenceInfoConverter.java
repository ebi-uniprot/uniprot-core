package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceInfoConverter extends EvidenceCollector implements Converter<EvidenceInfo, Map<Object, List<Evidence>> > {
	@Override
	public Map<Object, List<Evidence>> convert(EvidenceInfo f) {
		 Map<Object, List<Evidence>> evidences =new HashMap<>();
		 if (f==null)
			 return evidences;

		 for(Map.Entry<Object, List<String> > entry:f.evidences.entrySet() ){
			 evidences.put(entry.getKey(), EvidenceHelper.convert(entry.getValue()));
		 }
		 return evidences;
	}
}
