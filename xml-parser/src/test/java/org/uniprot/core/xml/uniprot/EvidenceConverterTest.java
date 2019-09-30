package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.EvidenceType;

class EvidenceConverterTest {
    private final EvidenceConverter converter = new EvidenceConverter();

    @Test
    void testProteomics() {
        // **EV ECO:0000213; PubMed:1234143; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000213|PubMed:1234143";
        String ecoCode = "ECO:0000213";
        String attribute = "1234143";
        String typeStr = "PubMed";

        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testSequenceAnalysis() {
        // **EV ECO:0000255; -; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000255";
        String ecoCode = "ECO:0000255";
        String attribute = "";
        String typeStr = "";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportS() {
        // **EV ECO:0000312; DatabaseName:DatabaseId; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000312|DatabaseName:DatabaseId";
        String ecoCode = "ECO:0000312";
        String attribute = "DatabaseId";
        String typeStr = "DatabaseName";

        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testSimilarity() {
        // **EV ECO:0000250; UniProtKB:Accession; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000250|UniProtKB:Accession";
        String ecoCode = "ECO:0000250";
        String attribute = "Accession";
        String typeStr = "UniProtKB";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testReference() {
        // **EV ECO:0000303; Reference:x; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000303|Ref.3";
        String ecoCode = "ECO:0000303";

        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        assertEquals(ecoCode, xmlObj.getType());
        assertEquals(3, xmlObj.getSource().getRef().intValue());
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testOpinion2() {
        // **EV ECO:0000303; PubMed:x; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000303|PubMed:x";
        String ecoCode = "ECO:0000303";
        String attribute = "x";
        String typeStr = "PubMed";

        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testExperimental1() {
        // **EV ECO:0000269; PubMed:123; XXX; 13-NOV-1978
        String evIdStr = "ECO:0000269|PubMed:123";
        String ecoCode = "ECO:0000269";
        String attribute = "123";
        String typeStr = "PubMed";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testExperimental2() {
        // **EV ECO:0000269; Ref.1; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000269|Ref.1";
        String ecoCode = "ECO:0000269";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify2(xmlObj, ecoCode, 1);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testCurator1() {
        // **EV ECO:0000305; PubMed:x; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000305|PubMed:x";
        String ecoCode = "ECO:0000305";
        String attribute = "x";
        String typeStr = "PubMed";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testCurator2() {
        // **EV ECO:0000305; -; XXX; 13-NOV-1978.
        String evIdStr = "ECO:0000305";
        String ecoCode = "ECO:0000305";
        String attribute = "";
        String typeStr = "";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportEmbl() {
        // **EV ECO:0000313; EMBL:BAG16761.1; -; 01-OCT-2010.
        String evIdStr = "ECO:0000313|EMBL:BAG16761.1";
        String ecoCode = "ECO:0000313";
        String attribute = "BAG16761.1";
        String typeStr = "EMBL";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportHamapT() {
        // **ECO:0000256|HAMAP-Rule:MF_00205
        String evIdStr = "ECO:0000256|HAMAP-Rule:MF_00205";
        String ecoCode = "ECO:0000256";
        String attribute = "MF_00205";
        String typeStr = "HAMAP-Rule";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportHamapS() {
        // **ECO:0000255|HAMAP-Rule:MF_00205
        String evIdStr = "ECO:0000255|HAMAP-Rule:MF_00205";
        String ecoCode = "ECO:0000255";
        String attribute = "MF_00205";
        String typeStr = "HAMAP-Rule";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportEnsembl() {

        // **EV ECO:0000313; Ensembl:ENSMUSP00000067691; -; 10-JUN-2011.

        String evIdStr = "ECO:0000313|Ensembl:ENSMUSP00000067691";
        String ecoCode = "ECO:0000313";
        String attribute = "ENSMUSP00000067691";
        String typeStr = "Ensembl";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportPIRNR() {
        // **EV ECO:0000256; PIRNR:PIRNR000477; -; 24-OCT-2012.

        String evIdStr = "ECO:0000256|PIRNR:PIRNR000477";
        String ecoCode = "ECO:0000256";
        String attribute = "PIRNR000477";
        String typeStr = "PIRNR";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testNoSource() {
        // ECO:0000255
        String evIdStr = "ECO:0000255";
        String ecoCode = "ECO:0000255";
        String attribute = "";
        String typeStr = "";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
    }

    @Test
    void testImportProImp() {
        // **EV ECO:0000313; ProtImp:UP000006470; -; 25-JUN-2014.
        String evIdStr = "ECO:0000313|ProtImp:UP000006470";
        String ecoCode = "ECO:0000313";
        String attribute = "UP000006470";
        String typeStr = "ProtImp";
        Evidence evidence = parseEvidenceLine(evIdStr);
        EvidenceType xmlObj = converter.toXml(evidence);
        assertNotNull(xmlObj);
        verify1(xmlObj, ecoCode, typeStr, attribute);
        Evidence converted = converter.fromXml(xmlObj);
        assertEquals(evidence, converted);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, EvidenceType.class, "evidence"));
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
}
