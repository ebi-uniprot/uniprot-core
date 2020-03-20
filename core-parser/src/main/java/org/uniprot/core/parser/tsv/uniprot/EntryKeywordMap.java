package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.Keyword;

public class EntryKeywordMap implements NamedValueMap {
    private final List<Keyword> keywords;
    public static final List<String> FIELDS = Arrays.asList("keyword", "keywordid");

    public EntryKeywordMap(List<Keyword> keywords) {
        if (keywords == null) {
            this.keywords = Collections.emptyList();
        } else {
            this.keywords = Collections.unmodifiableList(keywords);
        }
    }

    @Override
    public Map<String, String> attributeValues() {
        if (keywords.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        String kwValue = keywords.stream().map(Keyword::getName).collect(Collectors.joining(";"));
        map.put(FIELDS.get(0), kwValue);
        String kwIds =
                keywords.stream()
                        .map(Keyword::getId)
                        .filter(val -> val != null && !val.isEmpty())
                        .collect(Collectors.joining("; "));
        map.put(FIELDS.get(1), kwIds);
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
