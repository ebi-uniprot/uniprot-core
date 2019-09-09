package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.builder.GeneLocationBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.GeneLocationType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.OrganelleConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class OrganelleConverterTest {

	@Test
	void testPlasmid() {
		  GeneEncodingType geneEncodingType = GeneEncodingType.PLASMID;
	        String value = "pBgh";
	        List<Evidence> evidences = createEvidences();
	        GeneLocation organelle = new GeneLocationBuilder(geneEncodingType, value, evidences).build();
	        OrganelleConverter converter = new OrganelleConverter(new EvidenceIndexMapper());
	        GeneLocationType xml = converter.toXml(organelle);
	        System.out.println(UniProtXmlTestHelper.toXmlString(xml, GeneLocationType.class, "geneLocation"));
	        
	        GeneLocation converted = converter.fromXml(xml);
	        assertEquals(organelle, converted);
	        
	}
	
	@Test
	void testMitochondrion() {
		  GeneEncodingType geneEncodingType = GeneEncodingType.MITOCHONDRION;
	        String value = "";
	        List<Evidence> evidences = createEvidences();
	        GeneLocation organelle = new GeneLocationBuilder(geneEncodingType, value, evidences).build();
	        OrganelleConverter converter = new OrganelleConverter(new EvidenceIndexMapper());
	        GeneLocationType xml = converter.toXml(organelle);
	        System.out.println(UniProtXmlTestHelper.toXmlString(xml, GeneLocationType.class, "geneLocation"));
	        
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
