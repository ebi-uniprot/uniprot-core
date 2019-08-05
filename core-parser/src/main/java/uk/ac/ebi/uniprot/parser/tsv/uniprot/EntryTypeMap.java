package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import java.util.*;

import org.uniprot.core.uniprot.UniProtEntryType;

public class EntryTypeMap implements NamedValueMap {
	public static final List<String> FIELDS = Collections.singletonList("reviewed");
	private final UniProtEntryType entryType;

	public EntryTypeMap(UniProtEntryType entryType) {
		this.entryType = entryType;
	}

	@Override
	public Map<String, String> attributeValues() {
		Map<String, String> map = new HashMap<>();
		map.put(FIELDS.get(0), entryType.equals(UniProtEntryType.SWISSPROT) ? "reviewed" : "unreviewed");
		return map;
	}
	public static  boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
	}
}
