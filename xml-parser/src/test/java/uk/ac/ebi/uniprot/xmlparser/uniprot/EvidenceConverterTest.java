package uk.ac.ebi.uniprot.xmlparser.uniprot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidenceType;

public class EvidenceConverterTest {
	private final EvidenceConverter converter = new EvidenceConverter();

	public static final String TARGET_PACKAGE = "uk.ac.ebi.uniprot.xml.jaxb.uniprot";
	protected static JAXBContext jaxbContext;

	/**
	 * This setup should only be run once so we can see if the tests interfere with
	 * each other which they should not.
	 */
	@BeforeAll
	public static void setup() {
		try {
			jaxbContext = JAXBContext.newInstance(TARGET_PACKAGE);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void testProteomics() {
		// **EV ECO:0000213; PubMed:1234143; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000213|PubMed:1234143";
		String ecoCode = "ECO:0000213";
		String attribute = "1234143";
		String typeStr = "PubMed";

		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testSequenceAnalysis() {
		// **EV ECO:0000255; -; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000255";
		String ecoCode = "ECO:0000255";
		String attribute = "";
		String typeStr = "";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportS() {
		// **EV ECO:0000312; DatabaseName:DatabaseId; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000312|DatabaseName:DatabaseId";
		String ecoCode = "ECO:0000312";
		String attribute = "DatabaseId";
		String typeStr = "DatabaseName";

		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testSimilarity() {
		// **EV ECO:0000250; UniProtKB:Accession; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000250|UniProtKB:Accession";
		String ecoCode = "ECO:0000250";
		String attribute = "Accession";
		String typeStr = "UniProtKB";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testReference() {
		// **EV ECO:0000303; Reference:x; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000303|Ref.3";
		String ecoCode = "ECO:0000303";

		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		assertEquals(ecoCode, xmlObj.getType());
		assertEquals(3, xmlObj.getSource().getRef().intValue());
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testOpinion2() {
		// **EV ECO:0000303; PubMed:x; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000303|PubMed:x";
		String ecoCode = "ECO:0000303";
		String attribute = "x";
		String typeStr = "PubMed";

		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testExperimental1() {
		// **EV ECO:0000269; PubMed:123; XXX; 13-NOV-1978
		String evIdStr = "ECO:0000269|PubMed:123";
		String ecoCode = "ECO:0000269";
		String attribute = "123";
		String typeStr = "PubMed";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testExperimental2() {
		// **EV ECO:0000269; Ref.1; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000269|Ref.1";
		String ecoCode = "ECO:0000269";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify2(xmlObj, ecoCode, 1);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testCurator1() {
		// **EV ECO:0000305; PubMed:x; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000305|PubMed:x";
		String ecoCode = "ECO:0000305";
		String attribute = "x";
		String typeStr = "PubMed";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testCurator2() {
		// **EV ECO:0000305; -; XXX; 13-NOV-1978.
		String evIdStr = "ECO:0000305";
		String ecoCode = "ECO:0000305";
		String attribute = "";
		String typeStr = "";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportEmbl() {
		// **EV ECO:0000313; EMBL:BAG16761.1; -; 01-OCT-2010.
		String evIdStr = "ECO:0000313|EMBL:BAG16761.1";
		String ecoCode = "ECO:0000313";
		String attribute = "BAG16761.1";
		String typeStr = "EMBL";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportHamapT() {
		// **ECO:0000256|HAMAP-Rule:MF_00205
		String evIdStr = "ECO:0000256|HAMAP-Rule:MF_00205";
		String ecoCode = "ECO:0000256";
		String attribute = "MF_00205";
		String typeStr = "HAMAP-Rule";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportHamapS() {
		// **ECO:0000255|HAMAP-Rule:MF_00205
		String evIdStr = "ECO:0000255|HAMAP-Rule:MF_00205";
		String ecoCode = "ECO:0000255";
		String attribute = "MF_00205";
		String typeStr = "HAMAP-Rule";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportEnsembl() {

		// **EV ECO:0000313; Ensembl:ENSMUSP00000067691; -; 10-JUN-2011.

		String evIdStr = "ECO:0000313|Ensembl:ENSMUSP00000067691";
		String ecoCode = "ECO:0000313";
		String attribute = "ENSMUSP00000067691";
		String typeStr = "Ensembl";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportPIRNR() {
		// **EV ECO:0000256; PIRNR:PIRNR000477; -; 24-OCT-2012.

		String evIdStr = "ECO:0000256|PIRNR:PIRNR000477";
		String ecoCode = "ECO:0000256";
		String attribute = "PIRNR000477";
		String typeStr = "PIRNR";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testNoSource() {
		// ECO:0000255
		String evIdStr = "ECO:0000255";
		String ecoCode = "ECO:0000255";
		String attribute = "";
		String typeStr = "";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	@Test
	public void testImportProImp() {
		// **EV ECO:0000313; ProtImp:UP000006470; -; 25-JUN-2014.
		String evIdStr = "ECO:0000313|ProtImp:UP000006470";
		String ecoCode = "ECO:0000313";
		String attribute = "UP000006470";
		String typeStr = "ProtImp";
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence(evIdStr);
		EvidenceType xmlObj = converter.toXml(evidence);
		assertNotNull(xmlObj);
		verify1(xmlObj, ecoCode, typeStr, attribute);
		Evidence converted = converter.fromXml(xmlObj);
		assertEquals(evidence, converted);
		System.out.println(toXmlString(xmlObj));
	}

	private void verify1(EvidenceType xmlObj, String ecoCode, String dbType, String dbId) {
		assertEquals(ecoCode, xmlObj.getType());
		if (!dbType.isEmpty()) {
			assertEquals(dbType, xmlObj.getSource().getDbReference().getType());

			assertEquals(dbId, xmlObj.getSource().getDbReference().getId());
		}
	}

	private void verify2(EvidenceType xmlObj, String ecoCode, int refNum) {
		assertEquals(ecoCode, xmlObj.getType());
		assertNull(xmlObj.getSource().getDbReference());
		assertEquals(refNum, xmlObj.getSource().getRef().intValue());

	}

	private String toXmlString(EvidenceType evidenceType) {
		try {
			StringWriter stringWriter = new StringWriter();
			JAXBContext jc = JAXBContext.newInstance(TARGET_PACKAGE);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			JAXBElement<EvidenceType> jbe = new JAXBElement<>(new QName("http://uniprot.org/uniprot", "Evidence"),
					EvidenceType.class, evidenceType);
			marshaller.marshal(jbe, stringWriter);
			stringWriter.close();
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
