package org.uniprot.core.cv.keyword.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.impl.StatisticsBuilder;

class KeywordEntryBuilderTest {
    private GoTerm goTerm = new GoTermBuilder().id("id").name("name").build();

    @Test
    void canPutKeyWordId() {
        KeywordEntry obj =
                new KeywordEntryBuilder().keyword(new KeywordIdImpl("name", "id")).build();

        assertEquals("id", obj.getAccession());
        assertSame(obj.getKeyword().getId(), obj.getAccession());
        assertEquals("name", obj.getKeyword().getName());
    }

    @Test
    void canSetDefinition() {
        String def = "long definition";
        KeywordEntry obj = new KeywordEntryBuilder().definition(def).build();
        assertEquals(def, obj.getDefinition());
    }

    @Test
    void canAddSingleSynonyms() {
        KeywordEntry obj = new KeywordEntryBuilder().synonymsAdd("syn").build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void nullSynonyms_willBeIgnore() {
        KeywordEntry obj = new KeywordEntryBuilder().synonymsAdd(null).build();
        assertNotNull(obj.getSynonyms());
        assertTrue(obj.getSynonyms().isEmpty());
    }

    @Test
    void synonyms_willConvertUnModifiable_toModifiable() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .synonymsSet(Collections.emptyList())
                        .synonymsAdd("syn")
                        .build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void canAddListSynonyms() {
        KeywordEntry obj =
                new KeywordEntryBuilder().synonymsSet(Arrays.asList("1", "2", "3")).build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(3, obj.getSynonyms().size());
    }

    @Test
    void canAddSingleGeneOntologies() {
        KeywordEntry obj = new KeywordEntryBuilder().geneOntologiesAdd(goTerm).build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(1, obj.getGeneOntologies().size());
    }

    @Test
    void nullGeneOntologies_willBeIgnore() {
        KeywordEntry obj = new KeywordEntryBuilder().geneOntologiesAdd(null).build();
        assertNotNull(obj.getGeneOntologies());
        assertTrue(obj.getGeneOntologies().isEmpty());
    }

    @Test
    void geneOntologies_willConvertUnModifiable_toModifiable() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .geneOntologiesSet(Collections.emptyList())
                        .geneOntologiesAdd(goTerm)
                        .build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(1, obj.getGeneOntologies().size());
    }

    @Test
    void canAddListGeneOntologies() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .geneOntologiesSet(Arrays.asList(goTerm, new GoTermBuilder().build()))
                        .build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(2, obj.getGeneOntologies().size());
    }

    @Test
    void canAddSingleParents() {
        KeywordEntry obj = new KeywordEntryBuilder().parentsAdd(new KeywordEntryImpl()).build();
        assertNotNull(obj.getParents());
        assertFalse(obj.getParents().isEmpty());
        assertEquals(1, obj.getParents().size());
    }

    @Test
    void nullParents_willBeIgnore() {
        KeywordEntry obj = new KeywordEntryBuilder().parentsAdd(null).build();
        assertNotNull(obj.getParents());
        assertTrue(obj.getParents().isEmpty());
    }

    @Test
    void parents_willConvertUnModifiable_toModifiable() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .parentsSet(List.of())
                        .parentsAdd(new KeywordEntryImpl())
                        .build();
        assertNotNull(obj.getParents());
        assertFalse(obj.getParents().isEmpty());
        assertEquals(1, obj.getParents().size());
    }

    @Test
    void canAddSetParents() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .parentsSet(List.of(new KeywordEntryImpl()))
                        .build();
        assertNotNull(obj.getParents());
        assertFalse(obj.getParents().isEmpty());
        assertEquals(1, obj.getParents().size());
    }

    @Test
    void canAddSingleLinks() {
        KeywordEntry obj = new KeywordEntryBuilder().linksAdd("site").build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(1, obj.getLinks().size());
    }

    @Test
    void nullLinks_willBeIgnore() {
        KeywordEntry obj = new KeywordEntryBuilder().linksAdd(null).build();
        assertNotNull(obj.getLinks());
        assertTrue(obj.getLinks().isEmpty());
    }

    @Test
    void links_willConvertUnModifiable_toModifiable() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .linksSet(Collections.emptyList())
                        .linksAdd("link")
                        .build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(1, obj.getLinks().size());
    }

    @Test
    void canAddListLinks() {
        KeywordEntry obj = new KeywordEntryBuilder().linksSet(Arrays.asList("1", "2", "3")).build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(3, obj.getLinks().size());
    }

    @Test
    void canSetCategoryFromKeywordId() {
        KeywordEntry obj =
                new KeywordEntryBuilder().category(new KeywordIdImpl("PTM", "KW-9991")).build();

        assertEquals(KeywordCategory.PTM, obj.getCategory());
    }

    @Test
    void canSetCategory() {
        KeywordEntry obj = new KeywordEntryBuilder().category(KeywordCategory.DOMAIN).build();

        assertEquals(KeywordCategory.DOMAIN, obj.getCategory());
    }

    @Test
    void canAddSingleChildren() {
        KeywordEntry obj = new KeywordEntryBuilder().childrenAdd(new KeywordEntryImpl()).build();
        assertNotNull(obj.getChildren());
        assertFalse(obj.getChildren().isEmpty());
        assertEquals(1, obj.getChildren().size());
    }

    @Test
    void nullChildren_willBeIgnore() {
        KeywordEntry obj = new KeywordEntryBuilder().childrenAdd(null).build();
        assertNotNull(obj.getChildren());
        assertTrue(obj.getChildren().isEmpty());
    }

    @Test
    void children_willConvertUnModifiable_toModifiable() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .childrenSet(Collections.emptyList())
                        .childrenAdd(new KeywordEntryImpl())
                        .build();
        assertNotNull(obj.getChildren());
        assertFalse(obj.getChildren().isEmpty());
        assertEquals(1, obj.getChildren().size());
    }

    @Test
    void canAddListChildren() {
        KeywordEntry obj =
                new KeywordEntryBuilder()
                        .childrenSet(Collections.singletonList(new KeywordEntryImpl()))
                        .build();
        assertNotNull(obj.getChildren());
        assertFalse(obj.getChildren().isEmpty());
        assertEquals(1, obj.getChildren().size());
    }

    @Test
    void canPutStatsIntoBuilder() {
        Statistics stats =
                new StatisticsBuilder()
                        .reviewedProteinCount(220L)
                        .unreviewedProteinCount(10L)
                        .build();
        KeywordEntry obj = new KeywordEntryBuilder().statistics(stats).build();

        assertEquals(stats, obj.getStatistics());
    }

    @Test
    void canCreateBuilderFromInstance() {
        KeywordEntry obj = new KeywordEntryBuilder().build();
        KeywordEntryBuilder builder = KeywordEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        KeywordEntry obj = new KeywordEntryBuilder().build();
        KeywordEntry obj2 = new KeywordEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
