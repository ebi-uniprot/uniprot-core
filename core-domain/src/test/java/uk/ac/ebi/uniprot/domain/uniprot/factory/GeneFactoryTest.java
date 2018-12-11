package uk.ac.ebi.uniprot.domain.uniprot.factory;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GeneFactoryTest {
    @Test
    public void testCreateGene() {
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361")
        });
        GeneName geneName = GeneFactory.INSTANCE.createGeneName(val, evidences);
        List<GeneNameSynonym> synonyms = new ArrayList<>();

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        Gene gene = GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);
        assertTrue(gene.hasGeneName());
        TestHelper.verifyJson(gene);
        
    }
  
    @Test
    public void testCreateGeneWithSynonym() {
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361")
        });
        GeneName geneName = GeneFactory.INSTANCE.createGeneName(val, evidences);
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String valSyn = "someSyn";
        List<Evidence> synEvidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym = GeneFactory.INSTANCE.createGeneNameSynonym(valSyn, synEvidences);
        synonyms.add(synonym);

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        Gene gene = GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);
        assertTrue(gene.hasGeneName());
        assertEquals(val, gene.getGeneName().getValue());
        assertEquals(1, gene.getSynonyms().size());
        assertTrue(gene.getOrderedLocusNames().isEmpty());
        assertTrue(gene.getOrfNames().isEmpty());
     
        TestHelper.verifyJson(gene);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoGeneButSynonym() {
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String val = "someSyn";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym = GeneFactory.INSTANCE.createGeneNameSynonym(val, evidences);
        synonyms.add(synonym);

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        GeneFactory.INSTANCE.createGene(null, synonyms, olnNames, orfNames);
    }

    @Test
    public void testCreateNoGeneButOlnName() {
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        String val = "someSyn";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        OrderedLocusName olnName = GeneFactory.INSTANCE.createOrderedLocusName(val, evidences);
        olnNames.add(olnName);
        List<ORFName> orfNames = new ArrayList<>();

        Gene gene = GeneFactory.INSTANCE.createGene(null, synonyms, olnNames, orfNames);
        assertFalse(gene.hasGeneName());

        assertEquals(1, gene.getOrderedLocusNames().size());
        assertTrue(gene.getSynonyms().isEmpty());
        assertTrue(gene.getOrfNames().isEmpty());
        TestHelper.verifyJson(gene);
    }

    @Test
    public void testCreateNoGeneButOrfName() {
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        List<ORFName> orfNames = new ArrayList<>();

        String val = "someSyn";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        ORFName orfName = GeneFactory.INSTANCE.createORFName(val, evidences);
        orfNames.add(orfName);

        Gene gene = GeneFactory.INSTANCE.createGene(null, synonyms, olnNames, orfNames);
        assertFalse(gene.hasGeneName());

        assertEquals(1, gene.getOrfNames().size());
        assertTrue(gene.getSynonyms().isEmpty());
        assertTrue(gene.getOrderedLocusNames().isEmpty());
        TestHelper.verifyJson(gene);
    }

    @Test
    public void testCreateGeneName() {
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361")
        });
        GeneName geneName = GeneFactory.INSTANCE.createGeneName(val, evidences);
        assertEquals(val, geneName.getValue());
        assertEquals(1, geneName.getEvidences().size());
        assertEquals("ECO:0000256|PIRNR:PIRNR001361", geneName.getEvidences().get(0).getValue());
        assertFalse(evidences == geneName.getEvidences());
        TestHelper.verifyJson(geneName);
        
    }

    @Test
    public void testCreateGeneNameSynonym() {
        String val = "someSyn";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym geneName = GeneFactory.INSTANCE.createGeneNameSynonym(val, evidences);
        assertEquals(val, geneName.getValue());
        assertEquals(2, geneName.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:11389730", geneName.getEvidences().get(1).getValue());
        assertFalse(evidences == geneName.getEvidences());
        TestHelper.verifyJson(geneName);
    }

    @Test
    public void testCreateOrderedLocusName() {
        String val = "somelocus";
        List<Evidence> evidences = Collections.emptyList();
        OrderedLocusName geneName = GeneFactory.INSTANCE.createOrderedLocusName(val, evidences);
        assertEquals(val, geneName.getValue());
        assertEquals(0, geneName.getEvidences().size());
        TestHelper.verifyJson(geneName);
    }

    @Test
    public void testCreateORFName() {
        String val = "somelocus";
        List<Evidence> evidences = null;
        ORFName geneName = GeneFactory.INSTANCE.createORFName(val, evidences);
        assertEquals(val, geneName.getValue());
        assertEquals(0, geneName.getEvidences().size());
        TestHelper.verifyJson(geneName);
    }

    private Evidence createEvidence(String evidenceStr) {
        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
    }

}
