package org.uniprot.cv.go.impl;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uniprot.cv.go.RelationshipType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.uniprot.cv.go.RelationshipType.IS_A;
import static org.uniprot.cv.go.RelationshipType.PART_OF;

/**
 * Created 26/11/2020
 *
 * @author Edd
 */
class GORelationFileReaderTest {
    @Test
    void parsesCorrectly() {
        GORelationFileReader reader = new GORelationFileReader();
        List<GORelationFileReader.GORelationshipsEntry> relationships = reader.parse("src/test/resources/go");
        assertThat(relationships, hasSize(2));

        Map<String, Set<String>> isAMap = extractType(relationships, IS_A);
        assertThat(isAMap.keySet(), hasSize(11));
        assertThat(isAMap.get("GO:0000001"), containsInAnyOrder("GO:0048308", "GO:0048311"));

        Map<String, Set<String>> partOfMap = extractType(relationships, PART_OF);
        assertThat(partOfMap.keySet(), hasSize(2));
        assertThat(partOfMap.get("GO:0000101"), contains("GO:0000200"));
    }

    private Map<String, Set<String>> extractType(List<GORelationFileReader.GORelationshipsEntry> go, RelationshipType type) {
        return go.stream()
                .filter(t -> t.relationship.equals(type))
                .map(t -> t.relationships)
                .findFirst()
                .orElseThrow(AssertionFailedError::new);
    }
}
