package org.uniprot.core.cv.subcell.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.impl.StatisticsBuilder;

import java.util.Arrays;
import java.util.Collections;

class SubcellularLocationEntryBuilderTest {
    private GoTerm goTerm = new GoTermBuilder().id("id").name("name").build();

    @Test
    void canSetId() {
        String id = "id";
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().name(id).build();

        assertEquals(id, obj.getName());
    }

    @Test
    void canSetAccession() {
        String accession = "accession";
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().id(accession).build();

        assertEquals(accession, obj.getId());
    }

    @Test
    void canSetDefinition() {
        String definition = "definition";
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().definition(definition).build();

        assertEquals(definition, obj.getDefinition());
    }

    @Test
    void canSetContent() {
        String content = "content";
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().content(content).build();

        assertEquals(content, obj.getContent());
    }

    @Test
    void canSetKeyword() {
        KeywordId keyword = new KeywordIdBuilder().name("PTM").id("KW-9991").build();
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().keyword(keyword).build();

        assertTrue(obj.getKeyword().isPresent());
        assertEquals(keyword, obj.getKeyword().get());
    }

    @Test
    void canSetNote() {
        String longNote = "longNote";
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().note(longNote).build();

        assertEquals(longNote, obj.getNote());
    }

    @Test
    void canPutStatsIntoBuilder() {
        Statistics stats =
                new StatisticsBuilder()
                        .reviewedProteinCount(220L)
                        .unreviewedProteinCount(10L)
                        .build();
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().statistics(stats).build();

        assertEquals(stats, obj.getStatistics());
    }

    @Test
    void category() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .category(SubcellLocationCategory.ORIENTATION)
                        .build();

        assertEquals(SubcellLocationCategory.ORIENTATION, obj.getCategory());
    }

    @Test
    void canAddSingleGeneOntologies() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().geneOntologiesAdd(goTerm).build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(1, obj.getGeneOntologies().size());
    }

    @Test
    void nullGeneOntologies_willBeIgnore() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().geneOntologiesAdd(null).build();
        assertNotNull(obj.getGeneOntologies());
        assertTrue(obj.getGeneOntologies().isEmpty());
    }

    @Test
    void geneOntologies_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .geneOntologiesSet(Collections.emptyList())
                        .geneOntologiesAdd(goTerm)
                        .build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(1, obj.getGeneOntologies().size());
    }

    @Test
    void canAddListGeneOntologies() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .geneOntologiesSet(Arrays.asList(goTerm, new GoTermBuilder().build()))
                        .build();
        assertNotNull(obj.getGeneOntologies());
        assertFalse(obj.getGeneOntologies().isEmpty());
        assertEquals(2, obj.getGeneOntologies().size());
    }

    @Test
    void canAddSingle_references() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().referencesAdd("ref").build();
        assertNotNull(obj.getReferences());
        assertFalse(obj.getReferences().isEmpty());
        assertEquals(1, obj.getReferences().size());
    }

    @Test
    void null_references_willBeIgnore() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().referencesAdd(null).build();
        assertNotNull(obj.getReferences());
        assertTrue(obj.getReferences().isEmpty());
    }

    @Test
    void references_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .referencesSet(Collections.emptyList())
                        .referencesAdd("ref")
                        .build();
        assertNotNull(obj.getReferences());
        assertFalse(obj.getReferences().isEmpty());
        assertEquals(1, obj.getReferences().size());
    }

    @Test
    void canAddList_references() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .referencesSet(Arrays.asList("ref1", "ref2", "ref3"))
                        .build();
        assertNotNull(obj.getReferences());
        assertFalse(obj.getReferences().isEmpty());
        assertEquals(3, obj.getReferences().size());
    }

    @Test
    void canAddSingle_isA() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .isAAdd(new SubcellularLocationEntryImpl())
                        .build();
        assertNotNull(obj.getIsA());
        assertFalse(obj.getIsA().isEmpty());
        assertEquals(1, obj.getIsA().size());
    }

    @Test
    void null_isA_willBeIgnore() {
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().isAAdd(null).build();
        assertNotNull(obj.getIsA());
        assertTrue(obj.getIsA().isEmpty());
    }

    @Test
    void isA_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .isASet(Collections.emptyList())
                        .isAAdd(new SubcellularLocationEntryImpl())
                        .build();
        assertNotNull(obj.getIsA());
        assertFalse(obj.getIsA().isEmpty());
        assertEquals(1, obj.getIsA().size());
    }

    @Test
    void canAddList_isA() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .isASet(Collections.singletonList(new SubcellularLocationEntryImpl()))
                        .build();
        assertNotNull(obj.getIsA());
        assertFalse(obj.getIsA().isEmpty());
        assertEquals(1, obj.getIsA().size());
    }

    @Test
    void canAddSingleSynonyms() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().synonymsAdd("syn").build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void nullSynonyms_willBeIgnore() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().synonymsAdd(null).build();
        assertNotNull(obj.getSynonyms());
        assertTrue(obj.getSynonyms().isEmpty());
    }

    @Test
    void synonyms_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .synonymsSet(Collections.emptyList())
                        .synonymsAdd("syn")
                        .build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void canAddListSynonyms() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .synonymsSet(Arrays.asList("1", "2", "3"))
                        .build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(3, obj.getSynonyms().size());
    }

    @Test
    void canAddSingle_links() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().linksAdd("link").build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(1, obj.getLinks().size());
    }

    @Test
    void null_links_willBeIgnore() {
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().linksAdd(null).build();
        assertNotNull(obj.getLinks());
        assertTrue(obj.getLinks().isEmpty());
    }

    @Test
    void links_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .linksSet(Collections.emptyList())
                        .linksAdd("link")
                        .build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(1, obj.getLinks().size());
    }

    @Test
    void canAddList_links() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .linksSet(Arrays.asList("l1", "l2", "l3"))
                        .build();
        assertNotNull(obj.getLinks());
        assertFalse(obj.getLinks().isEmpty());
        assertEquals(3, obj.getLinks().size());
    }

    @Test
    void canAddSingle_partOf() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .partOfAdd(new SubcellularLocationEntryImpl())
                        .build();
        assertNotNull(obj.getPartOf());
        assertFalse(obj.getPartOf().isEmpty());
        assertEquals(1, obj.getPartOf().size());
    }

    @Test
    void null_partOf_willBeIgnore() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder().partOfAdd(null).build();
        assertNotNull(obj.getPartOf());
        assertTrue(obj.getPartOf().isEmpty());
    }

    @Test
    void partOf_willConvertUnModifiable_toModifiable() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .partOfSet(Collections.emptyList())
                        .partOfAdd(new SubcellularLocationEntryImpl())
                        .build();
        assertNotNull(obj.getPartOf());
        assertFalse(obj.getPartOf().isEmpty());
        assertEquals(1, obj.getPartOf().size());
    }

    @Test
    void canAddList_partOf() {
        SubcellularLocationEntry obj =
                new SubcellularLocationEntryBuilder()
                        .partOfSet(Collections.singletonList(new SubcellularLocationEntryImpl()))
                        .build();
        assertNotNull(obj.getPartOf());
        assertFalse(obj.getPartOf().isEmpty());
        assertEquals(1, obj.getPartOf().size());
    }

    @Test
    void canCreateBuilderFromInstance() {
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().build();
        SubcellularLocationEntryBuilder builder = SubcellularLocationEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SubcellularLocationEntry obj = new SubcellularLocationEntryBuilder().build();
        SubcellularLocationEntry obj2 = new SubcellularLocationEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
