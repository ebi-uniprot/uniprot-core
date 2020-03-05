package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;
import org.uniprot.core.uniprot.xdb.builder.UniProtCrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;
import org.uniprot.cv.xdb.UniProtDatabaseImpl;

import com.google.common.base.Strings;

class UniProtCrossReferenceConverterTest {
    private final UniProtCrossReferenceConverter converter = new UniProtCrossReferenceConverter();

    @Test
    void testEmbl3Attributes() {
        // DR   EMBL; AY150815; AAN61049.1; -; mRNA.
        String dbName = "EMBL";
        String id = "AY150815";
        String description = "AAN61049.1";
        String thirdAttribute = "-";
        String fourthAttribute = "mRNA";
        String isoformId = null;
        UniProtCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertNull(xmlObj.getMolecule());
        verifyXmlAttr(xmlObj, "protein sequence ID", description);
        verifyXmlAttr(xmlObj, "status", thirdAttribute);
        verifyXmlAttr(xmlObj, "molecule type", fourthAttribute);
        assertNotNull(xmlObj);
        UniProtCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(emblXref, converted);
    }

    @Test
    void testEPDNoAttributes() {
        // DR   EPD; Q9U3D6; -.
        String dbName = "EPD";
        String id = "Q9U3D6";
        String description = "-";
        String thirdAttribute = null;
        String fourthAttribute = null;
        String isoformId = null;
        UniProtCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertTrue(xmlObj.getProperty().isEmpty());
        assertNull(xmlObj.getMolecule());
        UniProtCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(emblXref, converted);
    }

    @Test
    void testRefSeqOneAttributesWithIsoform() {
        // DR   RefSeq; NP_492154.2; NM_059753.5. [Q9U3D6-2]
        String dbName = "RefSeq";
        String id = "NP_492154.2";
        String description = "NM_059753.5";
        String thirdAttribute = null;
        String fourthAttribute = null;
        String isoformId = "Q9U3D6-2";
        UniProtCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(1, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "nucleotide sequence ID", description);
        assertNotNull(xmlObj.getMolecule());
        assertEquals(isoformId, xmlObj.getMolecule().getId());
        UniProtCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(emblXref, converted);
    }

    @Test
    void testProsite2AttributesWithIsoform() {
        // DR   PROSITE; PS51192; HELICASE_ATP_BIND_1; 1
        String dbName = "PROSITE";
        String id = "PS51192";
        String description = "HELICASE_ATP_BIND_1";
        String thirdAttribute = "1";
        String fourthAttribute = null;
        String isoformId = "Q9U3D6-5";
        UniProtCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(2, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "entry name", description);
        verifyXmlAttr(xmlObj, "match status", thirdAttribute);
        assertNotNull(xmlObj.getMolecule());
        assertEquals(isoformId, xmlObj.getMolecule().getId());
        UniProtCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(emblXref, converted);
    }

    @Test
    void testGO2Attributes() {
        // DR   GO; GO:0005524; F:ATP binding; IEA:UniProtKB-KW.
        String dbName = "GO";
        String id = "GO:0005524";
        String description = "F:ATP binding";
        String thirdAttribute = "IEA:UniProtKB-KW";
        String fourthAttribute = null;
        String isoformId = null;
        UniProtCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(3, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "term", description);
        verifyXmlAttr(xmlObj, "evidence", "ECO:0000501");
        verifyXmlAttr(xmlObj, "project", "UniProtKB-KW");
        UniProtCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(emblXref, converted);
    }

    private void verifyXml(DbReferenceType xmlObj, String db, String id) {
        String xml = UniProtXmlTestHelper.toXmlString(xmlObj, DbReferenceType.class, "dbReference");
        System.out.println(xml);
        assertEquals(db, xmlObj.getType());
        assertEquals(id, xmlObj.getId());
    }

    private void verifyXmlAttr(DbReferenceType xmlObj, String xmlTag, String val) {
        PropertyType wrongPropertyType = new PropertyType();
        PropertyType pType =
                xmlObj.getProperty().stream()
                        .filter(v -> v.getType().equals(xmlTag))
                        .findFirst()
                        .orElse(wrongPropertyType);
        if (Strings.isNullOrEmpty(val) || "-".equals(val)) {
            assertEquals(pType, wrongPropertyType);
        } else {
            assertNotEquals(pType, wrongPropertyType);
            assertEquals(val, pType.getValue());
        }
    }

    private UniProtCrossReference createUniProtDBCrossReference(
            String dbName,
            String id,
            String description,
            String thirdAttribute,
            String fourthAttribute,
            String isoformId) {
        UniProtDatabase uniProtDatabase = new UniProtDatabaseImpl(dbName);
        return new UniProtCrossReferenceBuilder()
                .databaseType(uniProtDatabase)
                .id(id)
                .isoformId(isoformId)
                .propertiesAdd(uniProtDatabase.getAttribute(0), description)
                .propertiesAdd(uniProtDatabase.getAttribute(1), thirdAttribute)
                .propertiesAdd(uniProtDatabase.getAttribute(2), fourthAttribute)
                .build();
    }
}
