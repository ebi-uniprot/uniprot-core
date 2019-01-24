package uk.ac.ebi.uniprot.parser.impl.ox;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceConverterHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OxLineConverter extends EvidenceCollector implements Converter<OxLineObject, Organism> {

	@Override
	public Organism convert(OxLineObject f) {
	
		Map<Object, List<Evidence> > evidences = EvidenceConverterHelper.convert(f.getEvidenceInfo());
		this.addAll( evidences.values());
		return new OrganismBuilder()
				.taxonId(f.taxonomy_id)
				.evidences(evidences.getOrDefault(f.taxonomy_id, Collections.emptyList()))
				.build();
	}

}
