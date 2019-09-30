package org.uniprot.core.parser.tsv.proteome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.proteome.Component;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeComponentMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("proteome_components");

    private final List<Component> components;

    public ProteomeComponentMap(List<Component> components) {
        this.components = components;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        String value =
                components.stream().map(val -> val.getName()).collect(Collectors.joining("; "));
        map.put(FIELDS.get(0), value);
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
