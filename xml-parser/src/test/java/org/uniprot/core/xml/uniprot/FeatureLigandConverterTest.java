package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.xml.jaxb.uniprot.LigandType;

/**
 * @author jluo
 * @date: 9 Feb 2022
 */
class FeatureLigandConverterTest {

    private final FeatureLigandConverter converter = new FeatureLigandConverter();

    @Test
    void testLigandFull() {
        Ligand ligand = createLigand("heme c", "ChEBI:CHEBI:61717", "2", "structural");
        LigandType xmlObj = converter.toXml(ligand);
        String expectedXml =
                "<ligand xmlns=\"http://uniprot.org/uniprot\">\n"
                        + "    <name>heme c</name>\n"
                        + "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
                        + "    <label>2</label>\n"
                        + "    <note>structural</note>\n"
                        + "</ligand>";
        assertEquals(
                expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandType.class, "ligand"));
        Ligand roundTrip = converter.fromXml(xmlObj);
        assertEquals(ligand, roundTrip);
    }

    @Test
    void testLigandNoNote() {
        Ligand ligand = createLigand("heme c", "ChEBI:CHEBI:61717", "2", null);
        LigandType xmlObj = converter.toXml(ligand);
        String expectedXml =
                "<ligand xmlns=\"http://uniprot.org/uniprot\">\n"
                        + "    <name>heme c</name>\n"
                        + "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
                        + "    <label>2</label>\n"
                        + "</ligand>";
        assertEquals(
                expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandType.class, "ligand"));
        Ligand roundTrip = converter.fromXml(xmlObj);
        assertEquals(ligand, roundTrip);
    }

    @Test
    void testLigandNoNoteLabel() {
        Ligand ligand = createLigand("heme c", "ChEBI:CHEBI:61717", null, null);
        LigandType xmlObj = converter.toXml(ligand);
        String expectedXml =
                "<ligand xmlns=\"http://uniprot.org/uniprot\">\n"
                        + "    <name>heme c</name>\n"
                        + "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
                        + "</ligand>";
        assertEquals(
                expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandType.class, "ligand"));
        Ligand roundTrip = converter.fromXml(xmlObj);
        assertEquals(ligand, roundTrip);
    }

    @Test
    void testLigandNoNoteLabelNoId() {
        Ligand ligand = createLigand("heme c", null, null, null);
        LigandType xmlObj = converter.toXml(ligand);
        String expectedXml =
                "<ligand xmlns=\"http://uniprot.org/uniprot\">\n"
                        + "    <name>heme c</name>\n"
                        + "</ligand>";
        assertEquals(
                expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandType.class, "ligand"));
        Ligand roundTrip = converter.fromXml(xmlObj);
        assertEquals(ligand, roundTrip);
    }

    private Ligand createLigand(String name, String id, String label, String note) {
        return new LigandBuilder().name(name).id(id).label(label).note(note).build();
    }
}
