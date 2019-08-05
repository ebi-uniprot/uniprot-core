package uk.ac.ebi.uniprot.flatfile.parser.impl.ox;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceConverterHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;

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
