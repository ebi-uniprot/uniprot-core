package org.uniprot.cv.go.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uniprot.cv.go.RelationshipType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.cv.go.RelationshipType.IS_A;
import static org.uniprot.cv.go.RelationshipType.PART_OF;

/**
 * Created 26/11/2020
 *
 * @author Edd
 */
class GORelationsCacheTest {
    private static List<GORelationFileReader.GORelationshipsEntry> cache;

    @BeforeAll
    static void setUp() {
        cache = GORelationsCache.INSTANCE.get("src/test/resources/go");
    }

    @Test
    void cacheInitialisedCorrectly() {
        assertThat(cache, hasSize(2));

        Map<String, Set<String>> isAMap = extractType(cache, IS_A);
        assertThat(isAMap.keySet(), hasSize(11));

        Map<String, Set<String>> partOfMap = extractType(cache, PART_OF);
        assertThat(partOfMap.keySet(), hasSize(2));
    }

    private Map<String, Set<String>> extractType(List<GORelationFileReader.GORelationshipsEntry> go, RelationshipType type) {
        return go.stream()
                .filter(t -> t.relationship.equals(type))
                .map(t -> t.relationships)
                .findFirst()
                .orElseThrow(AssertionFailedError::new);
    }
}