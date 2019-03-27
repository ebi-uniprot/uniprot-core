package uk.ac.ebi.uniprot.xml.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneNameType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.GeneConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class GeneConverterTest {

    @Test
    void testGeneNameOnly() {
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        GeneConverter converter = new GeneConverter(evRefMapper);
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361")});
        evRefMapper.reset(evidences);
        GeneName geneName = new GeneNameBuilder(val, evidences).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        Gene gene = createGene(geneName, synonyms, olnNames, orfNames);
        GeneType xmlGene = converter.toXml(gene);
        assertEquals(1, xmlGene.getName().size());
        verify(xmlGene.getName().get(0), GeneConverter.GENENAME_XMLTAG, val, Arrays.asList(1));
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlGene, GeneType.class, "gene"));
        Gene converted = converter.fromXml(xmlGene);
        assertEquals(gene, converted);

    }

    @Test
    void testGeneWithSynonym() {
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        GeneConverter converter = new GeneConverter(evRefMapper);
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361")});
        GeneName geneName = createGeneName(val, evidences);
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String valSyn = "someSyn";
        List<Evidence> synEvidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                                                                   parseEvidenceLine("ECO:0000269|PubMed:11389730")});

        List<Evidence> allEvidences = new ArrayList<>();
        allEvidences.addAll(evidences);
        allEvidences.addAll(synEvidences);
        evRefMapper.reset(allEvidences);

        GeneNameSynonym synonym = createGeneNameSynonym(valSyn, synEvidences);
        synonyms.add(synonym);

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        Gene gene = createGene(geneName, synonyms, olnNames, orfNames);
        GeneType xmlGene = converter.toXml(gene);
        assertEquals(2, xmlGene.getName().size());
        verify(xmlGene.getName().get(0), GeneConverter.GENENAME_XMLTAG, val, Arrays.asList(1));
        verify(xmlGene.getName().get(1), GeneConverter.SYNONYM_XMLTAG, valSyn, Arrays.asList(1, 2));
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlGene, GeneType.class, "gene"));
        Gene converted = converter.fromXml(xmlGene);
        assertEquals(gene, converted);
    }

    @Test
    void testNoGeneButOlnName() {
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        GeneConverter converter = new GeneConverter(evRefMapper);
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        String val = "someSyn";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                                                                parseEvidenceLine("ECO:0000269|PubMed:11389730")});
        evRefMapper.reset(evidences);

        OrderedLocusName olnName = createOrderedLocusName(val, evidences);
        olnNames.add(olnName);
        List<ORFName> orfNames = new ArrayList<>();

        Gene gene = createGene(null, synonyms, olnNames, orfNames);

        GeneType xmlGene = converter.toXml(gene);
        assertEquals(1, xmlGene.getName().size());
        verify(xmlGene.getName().get(0), GeneConverter.OLN_XMLTAG, val, Arrays.asList(1, 2));
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlGene, GeneType.class, "gene"));
        Gene converted = converter.fromXml(xmlGene);
        assertEquals(gene, converted);
    }

    @Test
    void testGeneOrfName() {
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        GeneConverter converter = new GeneConverter(evRefMapper);
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361")});
        GeneName geneName = createGeneName(val, evidences);


        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        List<ORFName> orfNames = new ArrayList<>();

        String orfVal = "someSyn";
        List<Evidence> orfEvidences = Arrays.asList(new Evidence[]{parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001461"),
                                                                   parseEvidenceLine("ECO:0000269|PubMed:11389730")});

        List<Evidence> allEvidences = new ArrayList<>();
        allEvidences.addAll(evidences);
        allEvidences.addAll(orfEvidences);
        evRefMapper.reset(allEvidences);


        ORFName orfName = createOrf(orfVal, orfEvidences);
        orfNames.add(orfName);

        Gene gene = createGene(geneName, synonyms, olnNames, orfNames);
        GeneType xmlGene = converter.toXml(gene);
        assertEquals(2, xmlGene.getName().size());
        verify(xmlGene.getName().get(0), GeneConverter.GENENAME_XMLTAG, val, Arrays.asList(1));
        verify(xmlGene.getName().get(1), GeneConverter.ORF_XMLTAG, orfVal, Arrays.asList(2, 3));
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlGene, GeneType.class, "gene"));
        Gene converted = converter.fromXml(xmlGene);
        assertEquals(gene, converted);
    }

    void verify(GeneNameType nameType, String type, String value, List<Integer> ev) {
        assertEquals(type, nameType.getType());
        assertEquals(value, nameType.getValue());
        assertEquals(ev, nameType.getEvidence());
    }

    private GeneNameSynonym createGeneNameSynonym(String valSyn, List<Evidence> synEvidences) {
        return new GeneNameSynonymBuilder(valSyn, synEvidences).build();
    }

    private GeneName createGeneName(String val, List<Evidence> evidences) {
        return new GeneNameBuilder(val, evidences).build();
    }

    private OrderedLocusName createOrderedLocusName(String val, List<Evidence> evidences) {
        return new OrderedLocusNameBuilder(val, evidences).build();
    }

    private Gene createGene(GeneName geneName, List<GeneNameSynonym> synonyms, List<OrderedLocusName> olnNames, List<ORFName> orfNames) {
        return new GeneBuilder()
                .geneName(geneName)
                .synonyms(synonyms)
                .orderedLocusNames(olnNames)
                .orfNames(orfNames)
                .build();
    }

    private ORFName createOrf(String orfVal, List<Evidence> orfEvidences) {
        return new ORFNameBuilder(orfVal, orfEvidences).build();
    }
}
