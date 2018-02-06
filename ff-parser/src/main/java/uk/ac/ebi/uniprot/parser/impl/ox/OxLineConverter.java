package uk.ac.ebi.uniprot.parser.impl.ox;

import java.util.List;
import java.util.Map;


import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class OxLineConverter extends EvidenceCollector implements Converter<OxLineObject, UniProtTaxonId> {

	@Override
	public UniProtTaxonId convert(OxLineObject f) {
	
		Map<Object, List<Evidence> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll( evidences.values());
		return UniProtFactory.INSTANCE.createUniProtTaxonId(f.taxonomy_id, evidences.get(f.taxonomy_id));
	}

}
