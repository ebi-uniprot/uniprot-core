package org.uniprot.core.parser.tsv.keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.util.Utils;

public class KeywordEntryMap implements NamedValueMap {

    private final KeywordEntry keywordEntry;

    public KeywordEntryMap(KeywordEntry keywordEntry) {
        this.keywordEntry = keywordEntry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        if (Utils.notNull(keywordEntry.getKeyword())) {
            map.put("id", keywordEntry.getKeyword().getAccession());
            map.put("name", keywordEntry.getKeyword().getId());
        }
        map.put("description", getOrDefaultEmpty(keywordEntry.getDefinition()));
        if (Utils.notNull(keywordEntry.getCategory())) {
            map.put("category", getOrDefaultEmpty(keywordEntry.getCategory().getId()));
        }
        map.put("synonym", getOrDefaultEmpty(keywordEntry.getSynonyms()));
        map.put("gene_ontology", getGeneOntology());
        map.put("sites", getOrDefaultEmpty(keywordEntry.getSites()));
        map.put("children", getChildren());
        map.put("parent", getParent());
        map.put("statistics", getStatistics());
        return map;
    }

    private String getGeneOntology() {
        String result = "";
        if (Utils.notNullOrEmpty(keywordEntry.getGeneOntologies())) {
            result = keywordEntry.getGeneOntologies().stream()
                    .map(go -> go.getGoId() + ":" + go.getGoTerm())
                    .collect(Collectors.joining(", "));
        }
        return result;
    }

    private String getChildren() {
        return keywordEntry.getChildren().stream()
                .flatMap(this::getAllChildrenLevel)
                .map(entry -> entry.getKeyword().getId())
                .collect(Collectors.joining(", "));
    }

    private Stream<KeywordEntry> getAllChildrenLevel(KeywordEntry entry) {
        Stream<KeywordEntry> result = Stream.of(entry);
        if (entry.getChildren() != null && !entry.getChildren().isEmpty()) {
            return Stream.concat(result, entry.getChildren().stream().flatMap(this::getAllChildrenLevel));
        }
        return result;
    }

    private String getParent() {
        return keywordEntry.getParents().stream()
                .flatMap(this::getAllParentLevel)
                .map(entry -> entry.getKeyword().getId())
                .collect(Collectors.joining(", "));
    }

    private Stream<KeywordEntry> getAllParentLevel(KeywordEntry entry) {
        Stream<KeywordEntry> result = Stream.of(entry);
        if (entry.getParents() != null && !entry.getParents().isEmpty()) {
            return Stream.concat(result, entry.getParents().stream().flatMap(this::getAllParentLevel));
        }
        return result;
    }

    private String getStatistics() {
        String result = "";
        if (keywordEntry.getStatistics() != null) {
            result = "reviewed:" + keywordEntry.getStatistics().getReviewedProteinCount() + "; " +
                    "annotated:" + keywordEntry.getStatistics().getUnreviewedProteinCount();
        }
        return result;
    }

}
