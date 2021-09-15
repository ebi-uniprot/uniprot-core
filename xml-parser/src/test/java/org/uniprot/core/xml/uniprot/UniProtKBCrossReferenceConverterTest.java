package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.google.common.base.Strings;

public class UniProtKBCrossReferenceConverterTest extends AbstractConverterTest {
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
        UniProtKBCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertNull(xmlObj.getMolecule());
        verifyXmlAttr(xmlObj, "protein sequence ID", description);
        verifyXmlAttr(xmlObj, "status", thirdAttribute);
        verifyXmlAttr(xmlObj, "molecule type", fourthAttribute);
        assertNotNull(xmlObj);
        UniProtKBCrossReference converted = converter.fromXml(xmlObj);
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
        UniProtKBCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertTrue(xmlObj.getProperty().isEmpty());
        assertNull(xmlObj.getMolecule());
        UniProtKBCrossReference converted = converter.fromXml(xmlObj);
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
        UniProtKBCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(1, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "nucleotide sequence ID", description);
        assertNotNull(xmlObj.getMolecule());
        assertEquals(isoformId, xmlObj.getMolecule().getId());
        UniProtKBCrossReference converted = converter.fromXml(xmlObj);
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
        UniProtKBCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(2, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "entry name", description);
        verifyXmlAttr(xmlObj, "match status", thirdAttribute);
        assertNotNull(xmlObj.getMolecule());
        assertEquals(isoformId, xmlObj.getMolecule().getId());
        UniProtKBCrossReference converted = converter.fromXml(xmlObj);
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
        UniProtKBCrossReference emblXref =
                createUniProtDBCrossReference(
                        dbName, id, description, thirdAttribute, fourthAttribute, isoformId);
        DbReferenceType xmlObj = converter.toXml(emblXref);
        verifyXml(xmlObj, dbName, id);
        assertEquals(3, xmlObj.getProperty().size());
        verifyXmlAttr(xmlObj, "term", description);
        verifyXmlAttr(xmlObj, "evidence", "ECO:0007669");
        verifyXmlAttr(xmlObj, "project", "UniProtKB-KW");
        UniProtKBCrossReference converted = converter.fromXml(xmlObj);
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

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String dbName,
            String id,
            String description,
            String thirdAttribute,
            String fourthAttribute,
            String isoformId) {
        UniProtKBDatabase uniProtkbDatabase = new UniProtKBDatabaseImpl(dbName);
        return new UniProtCrossReferenceBuilder()
                .database(uniProtkbDatabase)
                .id(id)
                .isoformId(isoformId)
                .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttribute)
                .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttribute)
                .build();
    }

    public static DbReferenceType createObject() {
        DbReferenceType dbReferenceType =
                objectCreator.createLoremIpsumObject(DbReferenceType.class);
        update(dbReferenceType);
        return dbReferenceType;
    }

    public static List<DbReferenceType> createObjects() {
        List<DbReferenceType> objects =
                objectCreator.createLoremIpsumObject(DbReferenceTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }

    public static class DbReferenceTypeList extends ArrayList<DbReferenceType> {}

    private static void update(DbReferenceType dbReferenceType) {
        List<Integer> evidences =
                objectCreator.createLoremIpsumObject(
                        EvidencedStringTypeConverterTest.IntegerList.class);
        dbReferenceType.getEvidence().addAll(evidences);
        dbReferenceType.getProperty().addAll(PropertyTypeTest.createObjects());
    }
}
