package org.uniprot.core.cv.keyword.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.builder.GeneOntologyEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordStatisticsImplTest;

class KeywordEntryImplTest {

    private String random;
    private KeywordId keyword;
    private String definition;
    private List<String> synonyms;
    private List<GoTerm> geneOntologies;
    private Set<KeywordEntry> parents;
    private List<String> sites;
    private KeywordId category;
    private List<KeywordEntry> children;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.keyword = createKeyword("id-" + this.random, "acc-" + this.random);
        this.definition = "definition-" + this.random;
        this.synonyms =
                IntStream.range(0, 5)
                        .mapToObj(i -> i + "-syn-" + this.random)
                        .collect(Collectors.toList());
        this.geneOntologies =
                IntStream.range(0, 3)
                        .mapToObj(i -> go(i + "-id-" + this.random, i + "-term-" + this.random))
                        .collect(Collectors.toList());
        this.sites =
                IntStream.range(0, 5)
                        .mapToObj(i -> i + "-site-" + this.random)
                        .collect(Collectors.toList());

        long rev = ThreadLocalRandom.current().nextLong(100000);
        long unrev = ThreadLocalRandom.current().nextLong(100000);
        this.statistics = KeywordStatisticsImplTest.createKeywordStatistics(rev, unrev);
        this.category = createKeyword("idc-" + this.random, "cat-acc-" + this.random);
    }

    @Test
    void testCreateKeywordEntry() {
        KeywordEntry keywordEntry = createKeywordEntry();
        assertEquals(this.keyword, keywordEntry.getKeyword());
        assertEquals(this.definition, keywordEntry.getDefinition());
        assertArrayEquals(this.synonyms.toArray(), keywordEntry.getSynonyms().toArray());
        assertArrayEquals(
                this.geneOntologies.toArray(), keywordEntry.getGeneOntologies().toArray());
        assertNotNull(keywordEntry.getParents());
        assertTrue(keywordEntry.getParents().isEmpty());
        assertNotNull(keywordEntry.getChildren());
        assertTrue(keywordEntry.getChildren().isEmpty());
        assertArrayEquals(this.sites.toArray(), keywordEntry.getSites().toArray());
        assertEquals(KeywordCategory.fromId(this.category.getId()), keywordEntry.getCategory());
        assertEquals(this.statistics, keywordEntry.getStatistics());
    }

    @Test
    void testCreateKeywordEntryDefaultConstructor() {
        KeywordEntry keywordEntry = new KeywordEntryImpl();
        assertNull(keywordEntry.getKeyword());
        assertNull(keywordEntry.getDefinition());
        Assertions.assertNotNull(keywordEntry.getSynonyms());
        assertTrue(keywordEntry.getSynonyms().isEmpty());
        Assertions.assertNotNull(keywordEntry.getGeneOntologies());
        assertTrue(keywordEntry.getGeneOntologies().isEmpty());
        assertNotNull(keywordEntry.getParents());
        assertTrue(keywordEntry.getParents().isEmpty());
        assertNotNull(keywordEntry.getChildren());
        assertTrue(keywordEntry.getChildren().isEmpty());
        assertNotNull(keywordEntry.getSites());
        assertTrue(keywordEntry.getSites().isEmpty());
        assertNull(keywordEntry.getCategory());
        assertNull(keywordEntry.getStatistics());
    }

    @Test
    void testValueEqual() {
        KeywordEntry kw1 = createKeywordEntry();
        KeywordEntry kw2 = createKeywordEntry();
        assertTrue(kw1.equals(kw2));
        assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testRefEqual() {
        KeywordEntry kw1 = createKeywordEntry();
        assertTrue(kw1.equals(kw1));
        assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        KeywordEntry kw1 = createKeywordEntry();
        Assertions.assertFalse(kw1.equals(null));
    }

    @Test
    void testValueNotEqual() {
        KeywordEntry kw1 = createKeywordEntry();
        this.statistics = null;
        KeywordEntry kw2 = createKeywordEntry();
        Assertions.assertFalse(kw1.equals(kw2));
    }

    private KeywordEntry createKeywordEntry() {
        return new KeywordEntryImpl(
                this.keyword,
                this.definition,
                this.synonyms,
                this.geneOntologies,
                this.parents,
                this.sites,
                this.category,
                this.children,
                this.statistics);
    }

    public static KeywordId createKeyword(String id, String accession) {
        return new KeywordIdBuilder().id(id).accession(accession).build();
    }

    private static GeneOntologyEntry go(String id, String term) {
        return new GeneOntologyEntryBuilder().id(id).name(term).build();
    }
}
