package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Organism;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryOrganismMap implements NamedValueMap {
	public static final List<String> FIELDS = Arrays.asList( "organism", "organism_id", "tax_id");
	private final Organism organism;

	public EntryOrganismMap(Organism organism) {
		this.organism = organism;
	}

	@Override
	public Map<String, String> attributeValues() {
		Map<String, String> map = new HashMap<>();
		map.put(FIELDS.get(0), EntryMapUtil.convertOrganism(organism));
		map.put(FIELDS.get(1), "" + organism.getTaxonId());
		map.put(FIELDS.get(2), "" + organism.getTaxonId());

		return map;
	}

	
	public static boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
	}
}
