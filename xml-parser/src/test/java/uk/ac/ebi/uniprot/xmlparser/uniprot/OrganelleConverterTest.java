package uk.ac.ebi.uniprot.xmlparser.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneLocation;
import uk.ac.ebi.uniprot.domain.uniprot.builder.GeneLocationBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneLocationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceHelper.parseEvidenceLine;

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
