package org.uniprot.cv.keyword;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordEntry;

class KeywordCacheIT {
    private static List<KeywordEntry> keywords;

    @BeforeAll
    static void setup() {
        keywords = KeywordCache.INSTANCE.get("keyword/keywlist.txt");
    }

    @Test
    void testWithMultiChildren() {
        String acc = "KW-0869";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(val -> val.getChildren()).orElse(Collections.emptyList());
        Set<KeywordEntry> parents =
                opVal.map(val -> val.getParents()).orElse(Collections.emptySet());
        assertFalse(children.isEmpty());
        assertTrue(parents.isEmpty());
    }

    @Test
    void testCategoryMultiParent() {
        String acc = "KW-9990";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(KeywordEntry::getChildren).orElse(Collections.emptyList());
        Set<KeywordEntry> parents =
                opVal.map(KeywordEntry::getParents).orElse(Collections.emptySet());

        assertTrue(parents.size() > 1);
        assertTrue(children.isEmpty());
    }

    @Test
    void testWithParentsAndChildren() {
        String acc = "KW-0540";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(val -> val.getChildren()).orElse(Collections.emptyList());
        Set<KeywordEntry> parents =
                opVal.map(val -> val.getParents()).orElse(Collections.emptySet());
        assertFalse(parents.isEmpty());
        assertFalse(children.isEmpty());
    }
}
