package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.impl.GeneLocationBuilder;
import org.uniprot.core.xml.jaxb.uniprot.GeneLocationType;

class OrganelleConverterTest {

    @Test
    void testPlasmid() {
        GeneEncodingType geneEncodingType = GeneEncodingType.PLASMID;
        String value = "pBgh";
        List<Evidence> evidences = createEvidences();
        GeneLocation organelle =
                new GeneLocationBuilder()
                        .geneEncodingType(geneEncodingType)
                        .value(value)
                        .evidencesSet(evidences)
                        .build();
        OrganelleConverter converter = new OrganelleConverter(new EvidenceIndexMapper());
        GeneLocationType xml = converter.toXml(organelle);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xml, GeneLocationType.class, "geneLocation"));

        GeneLocation converted = converter.fromXml(xml);
        assertEquals(organelle, converted);
    }

    @Test
    void testMitochondrion() {
        GeneEncodingType geneEncodingType = GeneEncodingType.MITOCHONDRION;
        String value = "";
        List<Evidence> evidences = createEvidences();
        GeneLocation organelle =
                new GeneLocationBuilder()
                        .geneEncodingType(geneEncodingType)
                        .value(value)
                        .evidencesSet(evidences)
                        .build();
        OrganelleConverter converter = new OrganelleConverter(new EvidenceIndexMapper());
        GeneLocationType xml = converter.toXml(organelle);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xml, GeneLocationType.class, "geneLocation"));

        GeneLocation converted = converter.fromXml(xml);
        assertEquals(organelle, converted);
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
