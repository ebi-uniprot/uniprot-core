package org.uniprot.core.parser.tsv.uniprot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.Sequence;

public class EntrySequenceMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("sequence", "mass", "length");

    private final Sequence sequence;

    public EntrySequenceMap(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), sequence.getValue() == null ? "" : sequence.getValue());
        map.put(FIELDS.get(1), "" + sequence.getMolWeight());
        map.put(FIELDS.get(2), "" + sequence.getLength());
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);

    }
}
