package org.uniprot.core.cv.keyword.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.GeneOntology;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordStatistics;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KeywordEntryImplTest {

    private String random;
    private Keyword keyword;
    private String definition;
    private List<String> synonyms;
    private List<GeneOntology> geneOntologies;
    private Set<KeywordEntry> parents;
    private List<String> sites;
    private Keyword category;
    private List<KeywordEntry> children;
    private KeywordStatistics statistics;

    @BeforeEach
    void setUp(){
        this.random = UUID.randomUUID().toString();
        this.keyword = KeywordImplTest.createKeyword("id-" + this.random, "acc-" + this.random);
        this.definition = "definition-" + this.random;
        this.synonyms = IntStream.range(0, 5).mapToObj(i -> i + "-syn-" + this.random).collect(Collectors.toList());
        this.geneOntologies = IntStream.range(0, 3)
                .mapToObj(i -> GeneOntologyImplTest.createGeneOntology(i +"-id-" + this.random, i + "-term-" + this.random))
                .collect(Collectors.toList());
        this.sites = IntStream.range(0, 5).mapToObj(i -> i + "-site-" + this.random).collect(Collectors.toList());

        long rev = ThreadLocalRandom.current().nextLong(100000);
        long unrev = ThreadLocalRandom.current().nextLong(100000);
        this.statistics = KeywordStatisticsImplTest.createKeywordStatistics(rev, unrev);
        this.category = KeywordImplTest.createKeyword("idc-" + this.random, "cat-acc-" + this.random);
    }

    @Test
    void testCreateKeywordEntry(){
        KeywordEntry keywordEntry = createKeywordEntry();
        Assertions.assertEquals(this.keyword, keywordEntry.getKeyword());
        Assertions.assertEquals(this.definition, keywordEntry.getDefinition());
        Assertions.assertArrayEquals(this.synonyms.toArray(), keywordEntry.getSynonyms().toArray());
        Assertions.assertArrayEquals(this.geneOntologies.toArray(), keywordEntry.getGeneOntologies().toArray());
        Assertions.assertNull(keywordEntry.getParents());
        Assertions.assertNull(keywordEntry.getChildren());
        Assertions.assertArrayEquals(this.sites.toArray(), keywordEntry.getSites().toArray());
        Assertions.assertEquals(this.category, keywordEntry.getCategory());
        Assertions.assertEquals(this.statistics, keywordEntry.getStatistics());
    }

    @Test
    void testCreateKeywordEntryDefaultConstructor(){
        KeywordEntry keywordEntry = new KeywordEntryImpl();
        Assertions.assertNull(keywordEntry.getKeyword());
        Assertions.assertNull(keywordEntry.getDefinition());
        Assertions.assertNull(keywordEntry.getSynonyms());
        Assertions.assertNull(keywordEntry.getGeneOntologies());
        Assertions.assertNotNull(keywordEntry.getParents());
        Assertions.assertTrue(keywordEntry.getParents().isEmpty());
        Assertions.assertNotNull(keywordEntry.getChildren());
        Assertions.assertTrue(keywordEntry.getChildren().isEmpty());
        Assertions.assertNull(keywordEntry.getSites());
        Assertions.assertNull(keywordEntry.getCategory());
        Assertions.assertNull(keywordEntry.getStatistics());
    }

    @Test
    void testValueEqual(){
        KeywordEntry kw1 = createKeywordEntry();
        KeywordEntry kw2 = createKeywordEntry();
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testRefEqual(){
        KeywordEntry kw1 = createKeywordEntry();
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull(){
        KeywordEntry kw1 = createKeywordEntry();
        Assertions.assertFalse(kw1.equals(null));
    }

    @Test
    void testValueNotEqual(){
        KeywordEntry kw1 = createKeywordEntry();
        this.statistics = null;
        KeywordEntry kw2 = createKeywordEntry();
        Assertions.assertFalse(kw1.equals(kw2));
    }

    private KeywordEntry createKeywordEntry() {
        return new KeywordEntryImpl(this.keyword, this.definition, this.synonyms, this.geneOntologies,
                this.parents, this.sites, this.category, this.children, this.statistics);
    }
}
