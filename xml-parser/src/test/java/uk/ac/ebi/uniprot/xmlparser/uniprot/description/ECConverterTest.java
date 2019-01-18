package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType.Conflict;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ECConverterTest {

	@Test
	void test() {
		String ec = "4.6.1.2";
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));

		EC ecObj = ProteinDescriptionFactory.INSTANCE.createECNumber(ec, evidences);
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
