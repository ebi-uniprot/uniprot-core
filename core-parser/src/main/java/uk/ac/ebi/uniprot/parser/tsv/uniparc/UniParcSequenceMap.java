package uk.ac.ebi.uniprot.parser.tsv.uniparc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
 */

public class UniParcSequenceMap implements NamedValueMap {
	public static final List<String> FIELDS = Arrays.asList("checksum", "length", "sequence");
	private final Sequence sequence;

	public UniParcSequenceMap(Sequence sequence) {
		this.sequence = sequence;
	}

	@Override
	public Map<String, String> attributeValues() {
		Map<String, String> map = new HashMap<>();
		map.put(FIELDS.get(0), sequence.getCrc64());
		map.put(FIELDS.get(1), "" + sequence.getLength());
		map.put(FIELDS.get(2), sequence.getValue());
		return map;
	}

	public static boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
	}


}
