package uk.ac.ebi.uniprot.xmlparser.uniprot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PropertyType;

public class UniProtCrossReferenceConverterTest {
	
	private final UniProtCrossReferenceConverter converter = new UniProtCrossReferenceConverter();
	@Test
	void testEmbl3Attributes() {
		//DR   EMBL; AY150815; AAN61049.1; -; mRNA.
		String dbName ="EMBL";
		String id ="AY150815";
		String description ="AAN61049.1";
		String thirdAttribute ="-";
		String fourthAttribute= "mRNA";
		String isoformId =null;
		UniProtDBCrossReference emblXref = 
				UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(dbName, id,
						description, thirdAttribute, fourthAttribute, isoformId);
		DbReferenceType xmlObj =converter.toXml(emblXref);
		verifyXml(xmlObj, dbName, id);
		assertNull(xmlObj.getMolecule());
		verifyXmlAttr(xmlObj, "protein sequence ID", description);
		verifyXmlAttr(xmlObj, "status", thirdAttribute);	
		verifyXmlAttr(xmlObj, "molecule type", fourthAttribute);
		assertNotNull(xmlObj);
		UniProtDBCrossReference converted = converter.fromXml(xmlObj);
		assertEquals(emblXref, converted);
		
	}
	
	
	@Test
	void testEPDNoAttributes() {
		//DR   EPD; Q9U3D6; -.
		String dbName ="EPD";
		String id ="Q9U3D6";
		String description ="-";
		String thirdAttribute = null;
		String fourthAttribute= null;
		String isoformId =null;
		UniProtDBCrossReference emblXref = 
				UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(dbName, id,
						description, thirdAttribute, fourthAttribute, isoformId);
		DbReferenceType xmlObj =converter.toXml(emblXref);
		verifyXml(xmlObj, dbName, id);
		assertTrue(xmlObj.getProperty().isEmpty());
		assertNull(xmlObj.getMolecule());
		UniProtDBCrossReference converted = converter.fromXml(xmlObj);
		assertEquals(emblXref, converted);
	
		
	}
	
	@Test
	void testRefSeqOneAttributesWithIsoform() {
		//DR   RefSeq; NP_492154.2; NM_059753.5. [Q9U3D6-2]
		String dbName ="RefSeq";
		String id ="NP_492154.2";
		String description ="NM_059753.5";
		String thirdAttribute = null;
		String fourthAttribute= null;
		String isoformId ="Q9U3D6-2";
		UniProtDBCrossReference emblXref = 
				UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(dbName, id,
						description, thirdAttribute, fourthAttribute, isoformId);
		DbReferenceType xmlObj =converter.toXml(emblXref);
		verifyXml(xmlObj, dbName, id);
		assertEquals(1, xmlObj.getProperty().size());
		verifyXmlAttr(xmlObj, "nucleotide sequence ID", description);
		assertNotNull(xmlObj.getMolecule());
		assertEquals(isoformId, xmlObj.getMolecule().getId());
		UniProtDBCrossReference converted = converter.fromXml(xmlObj);
		assertEquals(emblXref, converted);
		
	}
	
	@Test
	void testProsite2AttributesWithIsoform() {
		//DR   PROSITE; PS51192; HELICASE_ATP_BIND_1; 1
		String dbName ="PROSITE";
		String id ="PS51192";
		String description ="HELICASE_ATP_BIND_1";
		String thirdAttribute = "1";
		String fourthAttribute= null;
		String isoformId ="Q9U3D6-5";
		UniProtDBCrossReference emblXref = 
				UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(dbName, id,
						description, thirdAttribute, fourthAttribute, isoformId);
		DbReferenceType xmlObj =converter.toXml(emblXref);
		verifyXml(xmlObj, dbName, id);
		assertEquals(2, xmlObj.getProperty().size());
		verifyXmlAttr(xmlObj, "entry name", description);
		verifyXmlAttr(xmlObj, "match status", thirdAttribute);
		assertNotNull(xmlObj.getMolecule());
		assertEquals(isoformId, xmlObj.getMolecule().getId());
		UniProtDBCrossReference converted = converter.fromXml(xmlObj);
		assertEquals(emblXref, converted);
		
	}
	
	@Test
	void testGO2Attributes() {
		//DR   GO; GO:0005524; F:ATP binding; IEA:UniProtKB-KW.
		String dbName ="GO";
		String id ="GO:0005524";
		String description ="F:ATP binding";
		String thirdAttribute = "IEA:UniProtKB-KW";
		String fourthAttribute= null;
		String isoformId =null;
		UniProtDBCrossReference emblXref = 
				UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(dbName, id,
						description, thirdAttribute, fourthAttribute, isoformId);
		DbReferenceType xmlObj =converter.toXml(emblXref);
		verifyXml(xmlObj, dbName, id);
		assertEquals(3, xmlObj.getProperty().size());
		verifyXmlAttr(xmlObj, "term", description);
		verifyXmlAttr(xmlObj, "evidence", "ECO:0000501");
		verifyXmlAttr(xmlObj, "project", "UniProtKB-KW");
		UniProtDBCrossReference converted = converter.fromXml(xmlObj);
		assertEquals(emblXref, converted);
	
		
	}
	void verifyXml(DbReferenceType xmlObj, String db, String id){
		String xml =UniProtXmlTestHelper.toXmlString(xmlObj, DbReferenceType.class, "dbReference");
		System.out.println(xml);
		assertEquals(db, xmlObj.getType());
		assertEquals(id, xmlObj.getId());
	}
	void verifyXmlAttr(DbReferenceType xmlObj, String xmlTag, String val ) {
		Optional<PropertyType> pType = xmlObj.getProperty().stream().filter(v -> v.getType().equals(xmlTag))
				.findFirst();
		if(Strings.isNullOrEmpty(val) || "-".equals(val)) {
			assertFalse(pType.isPresent());
		}else {
			assertTrue(pType.isPresent());
			assertEquals(val, pType.get().getValue());
		}
	}
}
