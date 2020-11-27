package org.uniprot.cv.go.impl;

import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.go.RelationshipType;

import java.util.*;

import static org.uniprot.cv.go.RelationshipType.IS_A;
import static org.uniprot.cv.go.RelationshipType.PART_OF;

class GORelationFileReader extends AbstractFileReader<GORelationFileReader.GORelationshipsEntry> {
    private static final String COMMENT_PREFIX = "!";
    private static final String SEPARATOR = "\t";
    private static final String FILENAME = "GO.relations";

    @Override
    public List<GORelationFileReader.GORelationshipsEntry> parse(String filename) {
        return super.parse(filename + "/" + FILENAME);
    }

    @Override
    public List<GORelationshipsEntry> parseLines(List<String> lines) {
        Map<String, Set<String>> isAMap = new HashMap<>();
        Map<String, Set<String>> partOfMap = new HashMap<>();
        lines.forEach(line -> this.readLine(line, isAMap, partOfMap));
        List<GORelationshipsEntry> relationships = new ArrayList<>();

        relationships.add(createRelationshipMap(IS_A, isAMap));
        relationships.add(createRelationshipMap(PART_OF, partOfMap));

        return relationships;
    }

    private GORelationshipsEntry createRelationshipMap(
            RelationshipType relationship, Map<String, Set<String>> relationships) {
        GORelationshipsEntry relationshipMap = new GORelationshipsEntry();
        relationshipMap.relationship = relationship;
        relationshipMap.relationships = relationships;
        return relationshipMap;
    }

    private void readLine(
            String line, Map<String, Set<String>> isAMap, Map<String, Set<String>> isPartMap) {
        if (line.startsWith(COMMENT_PREFIX)) return;
        String[] tokens = line.split(SEPARATOR);
        if (tokens.length == 3) {

            if (tokens[1].equals(IS_A.getDisplayName())) {
                add2Map(isAMap, tokens[0], tokens[2]);
            } else if (tokens[1].equals(PART_OF.getDisplayName())) {
                add2Map(isPartMap, tokens[0], tokens[2]);
            }
        }
    }

    private void add2Map(Map<String, Set<String>> map, String key, String value) {
        Set<String> values = map.computeIfAbsent(key, k -> new HashSet<>());
        values.add(value);
    }

    static class GORelationshipsEntry {
        RelationshipType relationship;
        Map<String, Set<String>> relationships;
    }
}
