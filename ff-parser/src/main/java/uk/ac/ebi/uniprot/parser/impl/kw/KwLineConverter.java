package uk.ac.ebi.uniprot.parser.impl.kw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;

public class KwLineConverter extends EvidenceCollector implements Converter<KwLineObject, List<Keyword> > {
	@Override
	public List<Keyword> convert(KwLineObject f) {
		Map<Object, List<Evidence> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll( evidences.values());
		List<Keyword> keywords =new ArrayList<>();
		for(String kw: f.keywords){
			keywords.add(UniProtFactory.INSTANCE.createKeyword(kw, evidences.get(kw)));
		}	
		return keywords;
	}

}
