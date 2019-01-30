package uk.ac.ebi.uniprot.xmlparser.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class OrganismConverterTest {

	@Test
	void test() {
		Organism organism = createOrganism();
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		OrganismConverter converter = new OrganismConverter(evRefMapper);
		OrganismType xmlOrganism = converter.toXml(organism);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlOrganism, OrganismType.class, "organism"));
		Organism converted = converter.fromXml(xmlOrganism);
		assertEquals(organism, converted);
        
	}

	private Organism createOrganism() {
		OrganismBuilder builder =new OrganismBuilder();
		builder.scientificName("Homo sapiens")
		.commonName("Human")
		.taxonId(9606l)
		.lineage(Arrays.asList("Eukaryota", "Metazoa", "Chordata", 		
		"Craniata", "Vertebrata", "Euteleostomi",
		"Mammalia", "Eutheria", "Euarchontoglires", "Primates", "Haplorrhini",
		"Catarrhini", "Hominidae", "Homo"
				))
		.evidences(createEvidences());

		return builder.build();
	}
	 private List<Evidence> createEvidences(){
	        List<Evidence> evidences = new ArrayList<>();
	        evidences.add(UniProtFactory.INSTANCE.createEvidence(
	                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
	        evidences.add(UniProtFactory.INSTANCE.createEvidence(
	                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
	       
	        
	        return evidences;
	    }
}
