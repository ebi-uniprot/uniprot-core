package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.Collections.singletonList;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneBuilderTest {

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
    void testFullGeneBuilderCreationUsingAddMethodIsAsExpected() {

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

    public static Gene createObject(int listSize, boolean includeEvidences) {
        GeneBuilder builder = new GeneBuilder();
        GeneName geneName = GeneNameBuilderTest.createObject(listSize, includeEvidences);
        List<GeneNameSynonym> synonyms =
                GeneNameSynonymBuilderTest.createObjects(listSize, includeEvidences);
        List<OrderedLocusName> orderedLocusNames =
                OrderedLocusNameBuilderTest.createObjects(listSize, includeEvidences);
        List<ORFName> orfNames = ORFNameBuilderTest.createObjects(listSize, includeEvidences);
        return builder.geneName(geneName)
                .synonymsSet(synonyms)
                .orderedLocusNamesSet(orderedLocusNames)
                .orfNamesSet(orfNames)
                .build();
    }

    public static Gene createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static Gene createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Gene> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
