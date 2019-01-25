package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class GeneBuilderTest {

    private static final String DB_ID_1 = "PDB Id";
    private static final String DB_NAME_1 = "PDB";
    private static final String VALUE_1 = "the value 1";
    private static final Evidence EVIDENCE_1 = new EvidenceBuilder()
            .evidenceCode(EvidenceCode.ECO_0000213)
            .databaseId(DB_ID_1)
            .databaseName(DB_NAME_1)
            .build();
    private static final GeneName GENE_NAME = new GeneNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
    private static final OrderedLocusName ORDERED_LOCUS_NAME =
            new OrderedLocusNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
    private static final ORFName ORF_NAME = new ORFNameBuilder(VALUE_1, singletonList(EVIDENCE_1)).build();
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

        GeneBuilder builder = new GeneBuilder()
                .geneName(GENE_NAME)
                .orderedLocusNames(singletonList(ORDERED_LOCUS_NAME))
                .orfNames(singletonList(ORF_NAME))
                .synonyms(singletonList(SYNONYM));

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

        GeneBuilder builder = new GeneBuilder()
                .geneName(GENE_NAME)
                .orderedLocusNames(singletonList(ORDERED_LOCUS_NAME))
                .addOrderedLocusNames(ORDERED_LOCUS_NAME)
                .orfNames(singletonList(ORF_NAME))
                .addOrfNames(ORF_NAME)
                .synonyms(singletonList(SYNONYM))
                .addSynonyms(SYNONYM);

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
        builder.synonyms(singletonList(SYNONYM));
        assertThrows(IllegalArgumentException.class, builder::build);
    }
}