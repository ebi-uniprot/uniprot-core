package uk.ac.ebi.uniprot.parser.tsv.uniparc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
*/

public class UniParcOrganismMap implements NamedValueMap {
	private static final String DELIMITER = "; ";
	public static final List<String> FIELDS = Arrays.asList( "organism", "organism_id");
	private final List<Taxonomy> organisms;

	public UniParcOrganismMap(List<Taxonomy> organisms) {
		this.organisms = organisms;
	}

	
	
	@Override
	public Map<String, String> attributeValues() {
		Map<String, String> map = new HashMap<>();
		String organismNames =
		organisms.stream().map(val ->EntryMapUtil.convertOrganism(val) )
		.collect(Collectors.joining(DELIMITER));
		String taxIds=
		organisms.stream().map(val -> ""+val.getTaxonId())
		.collect(Collectors.joining(DELIMITER));
		
		map.put(FIELDS.get(0), organismNames);
		map.put(FIELDS.get(1), taxIds);

		return map;
	}
	public static boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
	}
}

