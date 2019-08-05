package org.uniprot.core.cv.keyword;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCache;
import org.uniprot.core.cv.keyword.KeywordEntry;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        List<KeywordEntry> children = opVal.get().getChildren();
        System.out.println("testWithMultiChildren");
        children.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));

        Set<KeywordEntry> parents = opVal.get().getParents();
        assertNotNull(parents);
        assertTrue(parents.isEmpty());
    }

    @Test
    void testCategoryMultiParent() {
        String acc = "KW-9990";
        Optional<KeywordEntry> opVal = keywords.stream()
                .filter(val -> val.getKeyword().getAccession().equals(acc))
                .findFirst();
        assertTrue(opVal.isPresent());
        Set<KeywordEntry> parents = opVal.get().getParents();
        assertNotNull(parents);
        assertTrue(parents.size() > 1);
        System.out.println("testCategory");
        parents.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));

        List<KeywordEntry> children = opVal.get().getChildren();
        assertNotNull(children);
        assertTrue(children.isEmpty());
    }

    @Test
    void testWithParentsAndChildren() {
        String acc = "KW-0540";
        Optional<KeywordEntry> opVal = keywords.stream()
                .filter(val -> val.getKeyword().getAccession().equals(acc))
                .findFirst();
        assertTrue(opVal.isPresent());
        System.out.println("testWithParentsAndChildren");
        Set<KeywordEntry> parents = opVal.get().getParents();
        assertNotNull(parents);
        assertFalse(parents.isEmpty());
        System.out.println("Parents");
        parents.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
        List<KeywordEntry> children = opVal.get().getChildren();
        assertNotNull(children);
        System.out.println("Children");
        children.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
    }

}
