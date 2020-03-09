package org.uniprot.core.uniprot.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.impl.EvidenceBuilder;

class GeneBuilderTest {

    private static final String DB_ID_1 = "PDB Id";
    private static final String DB_NAME_1 = "PDB";
    private static final String VALUE_1 = "the value 1";
    private static final Evidence EVIDENCE_1 =
            new EvidenceBuilder()
                    .evidenceCode(EvidenceCode.ECO_0000213)
                    .databaseId(DB_ID_1)
                    .databaseName(DB_NAME_1)
                    .build();
    private static final GeneName GENE_NAME =
            new GeneNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
    private static final OrderedLocusName ORDERED_LOCUS_NAME =
            new OrderedLocusNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
    private static final ORFName ORF_NAME =
            new ORFNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
    private static final GeneNameSynonym SYNONYM =
            new GeneNameSynonymBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();

    @Test
    void testEmptyGeneBuilderCreationIsAsExpected() {
        GeneBuilder builder = new GeneBuilder();
        Gene gene = builder.build();

        assertNotNull(gene);
        assertNull(gene.getGeneName());
        assertFalse(gene.hasGeneName());

        assertNotNull(gene.getOrderedLocusNames());
        assertEquals(0, gene.getOrderedLocusNames().size());

        assertNotNull(gene.getOrfNames());
        assertEquals(0, gene.getOrfNames().size());

        assertNotNull(gene.getSynonyms());
        assertEquals(0, gene.getSynonyms().size());
    }

    @Test
    void testFullGeneBuilderCreationIsAsExpected() {

        GeneBuilder builder =
                new GeneBuilder()
                        .geneName(GENE_NAME)
                        .orderedLocusNamesSet(singletonList(ORDERED_LOCUS_NAME))
                        .orfNamesSet(singletonList(ORF_NAME))
                        .synonymsSet(singletonList(SYNONYM));

        Gene gene = builder.build();
        assertNotNull(gene);
        assertTrue(gene.hasGeneName());
        assertNotNull(gene.getGeneName());
        assertEquals(GENE_NAME, gene.getGeneName());

        assertNotNull(gene.getOrderedLocusNames());
        assertEquals(1, gene.getOrderedLocusNames().size());
        assertEquals(ORDERED_LOCUS_NAME, gene.getOrderedLocusNames().get(0));

        assertNotNull(gene.getOrfNames());
        assertEquals(1, gene.getOrfNames().size());
        assertEquals(ORF_NAME, gene.getOrfNames().get(0));

        assertNotNull(gene.getSynonyms());
        assertEquals(1, gene.getSynonyms().size());
        assertEquals(SYNONYM, gene.getSynonyms().get(0));
    }

    @Test
    void testFullGeneBuilderCreationUsingAddMethoIsAsExpected() {

        GeneBuilder builder =
                new GeneBuilder()
                        .geneName(GENE_NAME)
                        .orderedLocusNamesSet(singletonList(ORDERED_LOCUS_NAME))
                        .orderedLocusNamesAdd(ORDERED_LOCUS_NAME)
                        .orfNamesSet(singletonList(ORF_NAME))
                        .orfNamesAdd(ORF_NAME)
                        .synonymsSet(singletonList(SYNONYM))
                        .synonymsAdd(SYNONYM);

        Gene gene = builder.build();
        assertNotNull(gene);
        assertTrue(gene.hasGeneName());
        assertNotNull(gene.getGeneName());
        assertEquals(GENE_NAME, gene.getGeneName());

        assertNotNull(gene.getOrderedLocusNames());
        assertEquals(2, gene.getOrderedLocusNames().size());

        assertNotNull(gene.getOrfNames());
        assertEquals(2, gene.getOrfNames().size());

        assertNotNull(gene.getSynonyms());
        assertEquals(2, gene.getSynonyms().size());
    }

    @Test
    void testThrowsErrorGeneWithSynonymAndWithoutGeneName() {
        GeneBuilder builder = new GeneBuilder();
        builder.synonymsSet(singletonList(SYNONYM));
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void canCreateBuilderFromInstance() {
        Gene obj = new GeneBuilder().build();
        GeneBuilder builder = GeneBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Gene obj = new GeneBuilder().build();
        Gene obj2 = new GeneBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void toStringEmpty() {
        Gene obj = new GeneBuilder().build();
        assertEquals("", obj.toString());
    }
}
