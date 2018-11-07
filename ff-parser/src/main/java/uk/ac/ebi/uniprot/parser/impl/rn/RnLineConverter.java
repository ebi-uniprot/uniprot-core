package uk.ac.ebi.uniprot.parser.impl.rn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;

public class RnLineConverter extends EvidenceCollector  implements Converter<RnLineObject, List<Evidence>> {

	@Override
	public List<Evidence> convert(RnLineObject f) {

		Map<Object, List<Evidence> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll( evidences.values());
		List<Evidence> evIds = evidences.get(f.number);
		if(evIds ==null)
			evIds = new ArrayList<>();
		return evIds;
	}

}
