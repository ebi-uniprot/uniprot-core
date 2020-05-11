package org.uniprot.core.parser.tsv.proteome;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.proteome.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeComponentMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("components");

    private final List<Component> components;

    public ProteomeComponentMap(List<Component> components) {
        this.components = components;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        String value =
                components.stream().map(Component::getName).collect(Collectors.joining("; "));
        map.put(FIELDS.get(0), value);
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
