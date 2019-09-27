package org.uniprot.core.cv.keyword;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeywordCacheIT {
    private static List<KeywordEntry> keywords;

    @BeforeAll
    static void setup() {
        keywords = KeywordCache.INSTANCE.get("");
    }

    @Test
    void testWithMultiChildren() {
        String acc = "KW-0869";
        Optional<KeywordEntry> opVal = keywords.stream()
                .filter(val -> val.getKeyword().getAccession().equals(acc))
                .findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children = opVal.map(val ->val.getChildren()).orElse(Collections.emptyList());
		Set<KeywordEntry> parents = opVal.map(val ->val.getParents()).orElse(Collections.emptySet());
        assertFalse(children.isEmpty());
        assertTrue(parents.isEmpty());
    }

    @Test
    void testCategoryMultiParent() {
        String acc = "KW-9990";
        Optional<KeywordEntry> opVal = keywords.stream()
                .filter(val -> val.getKeyword().getAccession().equals(acc))
                .findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children = opVal.map(val ->val.getChildren()).orElse(Collections.emptyList());
      	Set<KeywordEntry> parents = opVal.map(val ->val.getParents()).orElse(Collections.emptySet());

        assertTrue(parents.size() > 1);
        assertTrue(children.isEmpty());
    }

    @Test
    void testWithParentsAndChildren() {
        String acc = "KW-0540";
        Optional<KeywordEntry> opVal = keywords.stream()
                .filter(val -> val.getKeyword().getAccession().equals(acc))
                .findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children = opVal.map(val ->val.getChildren()).orElse(Collections.emptyList());
      	Set<KeywordEntry> parents = opVal.map(val ->val.getParents()).orElse(Collections.emptySet());
        assertFalse(parents.isEmpty());
        assertFalse(children.isEmpty());
      
    }

}
