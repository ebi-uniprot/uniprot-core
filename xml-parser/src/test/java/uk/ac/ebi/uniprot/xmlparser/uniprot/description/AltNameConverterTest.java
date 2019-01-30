package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.AlternativeName;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.xmlparser.uniprot.description.DescriptionHelper.*;

class AltNameConverterTest {
	@Test
	void testAll() {
		List<Evidence> evidences = createEvidences();
		Name fullName = createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers = createECNumbers();
		ProteinName altName = createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		AltNameConverter converter = new AltNameConverter( nameConverter,  ecConverter) ;
		
		AlternativeName xmlObj =converter.toXml(altName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(altName, converted);
	
	}

	@Test
	void testNoShortName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = createName("a full Name", evidences);
		List<Name> shortNames = Collections.emptyList();
		List<EC> ecNumbers = createECNumbers();
		ProteinName altName = createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		AltNameConverter converter = new AltNameConverter( nameConverter,  ecConverter) ;
		
		AlternativeName xmlObj =converter.toXml(altName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(altName, converted);
	
	}
	
	@Test
	void testNoEC() {
		List<Evidence> evidences = createEvidences();
		Name fullName = createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers =Collections.emptyList();
		ProteinName altName = createProteinName(fullName, shortNames, ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		AltNameConverter converter = new AltNameConverter( nameConverter,  ecConverter) ;
		
		AlternativeName xmlObj =converter.toXml(altName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, AlternativeName.class, "alternativeName"));
		ProteinName converted = converter.fromXml(xmlObj);
		assertEquals(altName, converted);
	
	}

	private List<Name> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(createName("short name1", evidences));
		shortNames.add(createName("short name2", evidences));
		return shortNames;
	}

	private List<EC> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<EC> ecNumbers = new ArrayList<>();
		ecNumbers.add(createEC("1.2.3.4", evidences));
		ecNumbers.add(createEC("1.3.4.3", evidences));
		return ecNumbers;
	}

}
