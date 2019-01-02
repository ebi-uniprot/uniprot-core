package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.RecommendedName;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class RecNameConverterTest {

	@Test
	void testAll() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers = createECNumbers();
		ProteinName recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		RecNameConverter converter = new RecNameConverter( nameConverter,  ecConverter) ;
		
		RecommendedName xmlObj =converter.toXml(recName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(recName, converted);
	
	}

	@Test
	void testNoShortName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = Collections.emptyList();
		List<EC> ecNumbers = createECNumbers();
		ProteinName recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		RecNameConverter converter = new RecNameConverter( nameConverter,  ecConverter) ;
		
		RecommendedName xmlObj =converter.toXml(recName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(recName, converted);
	
	}
	
	@Test
	void testNoEC() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers =Collections.emptyList();
		ProteinName recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		RecNameConverter converter = new RecNameConverter( nameConverter,  ecConverter) ;
		
		RecommendedName xmlObj =converter.toXml(recName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, RecommendedName.class, "recommendedName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(recName, converted);
	
	}

	private List<Name> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
		return shortNames;
	}

	private List<EC> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<EC> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
		return ecNumbers;
	}

	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}

}
