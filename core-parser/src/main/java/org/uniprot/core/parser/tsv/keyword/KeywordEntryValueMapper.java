package org.uniprot.core.parser.tsv.keyword;

import static org.uniprot.core.parser.tsv.TSVUtil.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.util.Utils;

public class KeywordEntryValueMapper implements EntityValueMapper<KeywordEntry> {

    @Override
    public Map<String, String> mapEntity(KeywordEntry keywordEntry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (Utils.notNull(keywordEntry.getKeyword())) {
            map.put("id", keywordEntry.getKeyword().getId());
            map.put("name", keywordEntry.getKeyword().getName());
            map.put(
                    "keyword",
                    keywordEntry.getKeyword().getId() + " " + keywordEntry.getKeyword().getName());
        }
        map.put("definition", getOrDefaultEmpty(keywordEntry.getDefinition()));
        if (Utils.notNull(keywordEntry.getCategory())) {
            map.put("category", getOrDefaultEmpty(keywordEntry.getCategory().getName()));
        }
        map.put("synonyms", getOrDefaultEmpty(keywordEntry.getSynonyms()));
        map.put("gene_ontologies", getGeneOntology(keywordEntry));
        map.put("links", getOrDefaultEmpty(keywordEntry.getLinks()));
        map.put("children", getChildren(keywordEntry));
        map.put("parents", getParent(keywordEntry));
        map.put("statistics", getStatistics(keywordEntry));
        return map;
    }

    private String getGeneOntology(KeywordEntry keywordEntry) {
        String result = "";
        if (Utils.notNullNotEmpty(keywordEntry.getGeneOntologies())) {
            result =
                    keywordEntry.getGeneOntologies().stream()
                            .map(go -> go.getId() + ":" + go.getName())
                            .collect(Collectors.joining(", "));
        }
        return result;
    }

    private String getChildren(KeywordEntry keywordEntry) {
        return keywordEntry.getChildren().stream()
                .flatMap(this::getAllChildrenLevel)
                .map(entry -> entry.getKeyword().getName())
                .collect(Collectors.joining(", "));
    }

    private Stream<KeywordEntry> getAllChildrenLevel(KeywordEntry entry) {
        Stream<KeywordEntry> result = Stream.of(entry);
        if (entry.getChildren() != null && !entry.getChildren().isEmpty()) {
            return Stream.concat(
                    result, entry.getChildren().stream().flatMap(this::getAllChildrenLevel));
        }
        return result;
    }

    private String getParent(KeywordEntry keywordEntry) {
        return keywordEntry.getParents().stream()
                .flatMap(this::getAllParentLevel)
                .map(entry -> entry.getKeyword().getName())
                .collect(Collectors.joining(", "));
    }

    private Stream<KeywordEntry> getAllParentLevel(KeywordEntry entry) {
        Stream<KeywordEntry> result = Stream.of(entry);
        if (entry.getParents() != null && !entry.getParents().isEmpty()) {
            return Stream.concat(
                    result, entry.getParents().stream().flatMap(this::getAllParentLevel));
        }
        return result;
    }

    private String getStatistics(KeywordEntry keywordEntry) {
        String result = "";
        if (keywordEntry.getStatistics() != null) {
            result =
                    "reviewed:"
                            + keywordEntry.getStatistics().getReviewedProteinCount()
                            + "; "
                            + "annotated:"
                            + keywordEntry.getStatistics().getUnreviewedProteinCount();
        }
        return result;
    }
}
