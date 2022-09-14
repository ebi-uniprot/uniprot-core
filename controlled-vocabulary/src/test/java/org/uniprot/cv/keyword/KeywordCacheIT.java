package org.uniprot.cv.keyword;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.uniprot.core.cv.keyword.KeywordEntry;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeywordCacheIT {
    private static List<KeywordEntry> keywords;

    @BeforeAll
    static void setup() {
        keywords = KeywordCache.INSTANCE.get("keyword/keywlist.txt");
    }

    @Test
    void testWithMultiParent() {
        String acc = "KW-0869";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(KeywordEntry::getChildren).orElse(Collections.emptyList());
        List<KeywordEntry> parents = opVal.map(KeywordEntry::getParents).orElse(List.of());
        assertTrue(children.isEmpty());
        assertTrue(parents.size() > 1);
    }

    @Test
    void testCategoryMultiChildren() {
        String acc = "KW-9990";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(KeywordEntry::getChildren).orElse(Collections.emptyList());
        List<KeywordEntry> parents = opVal.map(KeywordEntry::getParents).orElse(List.of());

        assertTrue(parents.isEmpty());
        assertTrue(children.size() > 1);
    }

    @Test
    void testWithParentsAndChildren() {
        String acc = "KW-0540";
        Optional<KeywordEntry> opVal =
                keywords.stream().filter(val -> val.getKeyword().getId().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<KeywordEntry> children =
                opVal.map(KeywordEntry::getChildren).orElse(Collections.emptyList());
        List<KeywordEntry> parents = opVal.map(KeywordEntry::getParents).orElse(List.of());
        assertFalse(parents.isEmpty());
        assertFalse(children.isEmpty());
    }
}
