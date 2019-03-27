package uk.ac.ebi.uniprot.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createEC;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createEvidences;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createName;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.createProteinSubName;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.SubmittedName;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.description.ECConverter;
import uk.ac.ebi.uniprot.xml.uniprot.description.NameConverter;
import uk.ac.ebi.uniprot.xml.uniprot.description.SubNameConverter;

class SubNameConverterTest {

	@Test
	void test() {
		List<Evidence> evidences = createEvidences();
		Name fullName = createName("a full Name", evidences);

		List<EC> ecNumbers = createECNumbers();
		ProteinSubName subName = createProteinSubName(fullName,   ecNumbers);
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter =new NameConverter(evRefMapper);
		SubNameConverter converter = new SubNameConverter( nameConverter,  ecConverter) ;
		SubmittedName xmlObj =converter.toXml(subName);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, SubmittedName.class, "submittedName"));
		ProteinSubName converted = converter.fromXml(xmlObj);
		assertEquals(subName, converted);
	}
	private List<EC> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<EC> ecNumbers = new ArrayList<>();
		ecNumbers.add(createEC("1.2.3.4", evidences));
		ecNumbers.add(createEC("1.3.4.3", evidences));
		return ecNumbers;
	}
}
