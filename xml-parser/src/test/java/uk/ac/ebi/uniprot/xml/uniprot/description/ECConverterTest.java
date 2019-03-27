package uk.ac.ebi.uniprot.xml.uniprot.description;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.description.ECConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class ECConverterTest {

	@Test
	void test() {
		String ec = "4.6.1.2";
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(new EvidenceBuilder()
							   .databaseName("Ensembl")
							   .databaseId("ENSP0001324")
							   .evidenceCode(EvidenceCode.ECO_0000313)
							   .build());
		evidences.add(new EvidenceBuilder()
							   .databaseName("PIRNR")
							   .databaseName("PIRNR001361")
							   .evidenceCode(EvidenceCode.ECO_0000256)
							   .build());

		EC ecObj = new ECBuilder().value(ec).evidences(evidences).build();
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter converter = new ECConverter(evRefMapper);
		EvidencedStringType xmlObj = converter.toXml(ecObj);
		assertEquals(ec, xmlObj.getValue());
		assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
		EC converted = converter.fromXml(xmlObj);
		assertEquals(ecObj, converted);
		DbReferenceType dbReference = converter.toXmlDbReference(ecObj);
		 System.out.println(UniProtXmlTestHelper.toXmlString(dbReference, DbReferenceType.class, "dbReference"));
	}

}
