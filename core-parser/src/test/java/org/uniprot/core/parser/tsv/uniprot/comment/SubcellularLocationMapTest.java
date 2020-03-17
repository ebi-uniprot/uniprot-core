package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;

class SubcellularLocationMapTest {

    @Test
    void testSubCellularLocationWithoutNoteWithLocationOnly() {
        String subcellularLocationLine =
                "CC   -!- SUBCELLULAR LOCATION: [Capsid protein C]: Virion\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}. Host nucleus\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}. Host cytoplasm, host perinuclear\n"
                        + "CC       region {ECO:0000250|UniProtKB:P17763}. Host cytoplasm\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}.";

        UniProtKBEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(subcellularLocationLine);

        List<SubcellularLocationComment> sclComments =
                entry.getCommentsByType(CommentType.SUBCELLULAR_LOCATION);
        assertNotNull(entry);
        SubcellularLocationMap subcellularLocationMap = new SubcellularLocationMap(sclComments);
        Map<String, String> mappedCelularLocation = subcellularLocationMap.attributeValues();
        assertNotNull(mappedCelularLocation);
        String value = mappedCelularLocation.get("cc_subcellular_location");
        String expectedValue =
                "SUBCELLULAR LOCATION: [Capsid protein C]: Virion {ECO:0000250|UniProtKB:P17763}. "
                        + "Host nucleus {ECO:0000250|UniProtKB:P17763}. Host cytoplasm, host perinuclear region {ECO:0000250|UniProtKB:P17763}. "
                        + "Host cytoplasm {ECO:0000250|UniProtKB:P17763}.";
        assertEquals(expectedValue, value);
    }

    @Test
    void testSubCellularLocationFull() {
        String subcellularLocationLine =
                "CC   -!- SUBCELLULAR LOCATION: [Non-structural protein 1]: Secreted\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}. Host endoplasmic reticulum\n"
                        + "CC       membrane; Peripheral membrane protein; Lumenal side\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}. Note=Located in RE-derived\n"
                        + "CC       vesicles hosting the replication complex.\n"
                        + "CC       {ECO:0000250|UniProtKB:Q9Q6P4}.";

        UniProtKBEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(subcellularLocationLine);

        List<SubcellularLocationComment> sclComments =
                entry.getCommentsByType(CommentType.SUBCELLULAR_LOCATION);
        assertNotNull(entry);
        SubcellularLocationMap subcellularLocationMap = new SubcellularLocationMap(sclComments);
        Map<String, String> mappedCelularLocation = subcellularLocationMap.attributeValues();
        assertNotNull(mappedCelularLocation);
        String value = mappedCelularLocation.get("cc_subcellular_location");
        String expectedValue =
                "SUBCELLULAR LOCATION: [Non-structural protein 1]: Secreted {ECO:0000250|UniProtKB:P17763}. "
                        + "Host endoplasmic reticulum membrane; Peripheral membrane protein; "
                        + "Lumenal side {ECO:0000250|UniProtKB:P17763}. "
                        + "Note=Located in RE-derived vesicles hosting the replication complex. {ECO:0000250|UniProtKB:Q9Q6P4}.";
        assertEquals(expectedValue, value);
    }

    @Test
    void testManySubCellularLocations() {
        String subcellularLocationLine =
                "CC   -!- SUBCELLULAR LOCATION: [Non-structural protein 2A]: Host endoplasmic\n"
                        + "CC       reticulum membrane {ECO:0000250|UniProtKB:P17763}; Multi-pass\n"
                        + "CC       membrane protein {ECO:0000250|UniProtKB:P17763}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Serine protease subunit NS2B]: Host\n"
                        + "CC       endoplasmic reticulum membrane; Multi-pass membrane protein\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Serine protease NS3]: Host endoplasmic\n"
                        + "CC       reticulum membrane {ECO:0000255|PROSITE-ProRule:PRU00860};\n"
                        + "CC       Peripheral membrane protein {ECO:0000255|PROSITE-\n"
                        + "CC       ProRule:PRU00860}; Cytoplasmic side {ECO:0000255|PROSITE-\n"
                        + "CC       ProRule:PRU00860}. Note=Remains non-covalently associated to\n"
                        + "CC       serine protease subunit NS2B. {ECO:0000255|PROSITE-\n"
                        + "CC       ProRule:PRU00860}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Non-structural protein 4A]: Host endoplasmic\n"
                        + "CC       reticulum membrane {ECO:0000250|UniProtKB:P17763}; Multi-pass\n"
                        + "CC       membrane protein {ECO:0000250|UniProtKB:P17763}. Note=Located in\n"
                        + "CC       RE-associated vesicles hosting the replication complex.\n"
                        + "CC       {ECO:0000250|UniProtKB:P17763}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [Non-structural protein 4B]: Host endoplasmic\n"
                        + "CC       reticulum membrane {ECO:0000250|UniProtKB:P17763}; Multi-pass\n"
                        + "CC       membrane protein {ECO:0000250|UniProtKB:P17763}. Note=Located in\n"
                        + "CC       RE-derived vesicles hosting the replication complex.\n"
                        + "CC       {ECO:0000250|UniProtKB:Q9Q6P4}.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: [RNA-directed RNA polymerase NS5]: Host\n"
                        + "CC       endoplasmic reticulum membrane; Peripheral membrane protein;\n"
                        + "CC       Cytoplasmic side. Host nucleus {ECO:0000250|UniProtKB:P17763}.\n"
                        + "CC       Note=Located in RE-associated vesicles hosting the replication\n"
                        + "CC       complex. NS5 protein is mainly localized in the nucleus rather\n"
                        + "CC       than in ER vesicles. {ECO:0000250|UniProtKB:P17763}.";

        UniProtKBEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(subcellularLocationLine);

        List<SubcellularLocationComment> sclComments =
                entry.getCommentsByType(CommentType.SUBCELLULAR_LOCATION);
        assertNotNull(entry);
        SubcellularLocationMap subcellularLocationMap = new SubcellularLocationMap(sclComments);
        Map<String, String> mappedCelularLocation = subcellularLocationMap.attributeValues();
        assertNotNull(mappedCelularLocation);
        String value = mappedCelularLocation.get("cc_subcellular_location");
        String expectedValue =
                "SUBCELLULAR LOCATION: [Non-structural protein 2A]: Host endoplasmic reticulum membrane "
                        + "{ECO:0000250|UniProtKB:P17763}; Multi-pass membrane protein {ECO:0000250|UniProtKB:P17763}.; "
                        + "SUBCELLULAR LOCATION: [Serine protease subunit NS2B]: Host endoplasmic reticulum membrane; "
                        + "Multi-pass membrane protein {ECO:0000250|UniProtKB:P17763}.; SUBCELLULAR LOCATION: [Serine protease "
                        + "NS3]: Host endoplasmic reticulum membrane {ECO:0000255|PROSITE-ProRule:PRU00860}; Peripheral membrane "
                        + "protein {ECO:0000255|PROSITE-ProRule:PRU00860}; Cytoplasmic side {ECO:0000255|PROSITE-ProRule:PRU00860}."
                        + " Note=Remains non-covalently associated to serine protease subunit NS2B. "
                        + "{ECO:0000255|PROSITE-ProRule:PRU00860}.; "
                        + "SUBCELLULAR LOCATION: [Non-structural protein 4A]: Host endoplasmic reticulum membrane "
                        + "{ECO:0000250|UniProtKB:P17763}; Multi-pass membrane protein {ECO:0000250|UniProtKB:P17763}. "
                        + "Note=Located in RE-associated vesicles hosting the replication complex. {ECO:0000250|UniProtKB:P17763}.; "
                        + "SUBCELLULAR LOCATION: [Non-structural protein 4B]: Host endoplasmic reticulum membrane {ECO:0000250|UniProtKB:P17763}; "
                        + "Multi-pass membrane protein {ECO:0000250|UniProtKB:P17763}. Note=Located in RE-derived vesicles "
                        + "hosting the replication complex. {ECO:0000250|UniProtKB:Q9Q6P4}.; SUBCELLULAR LOCATION: [RNA-directed "
                        + "RNA polymerase NS5]: Host endoplasmic reticulum membrane; Peripheral membrane protein; Cytoplasmic side. "
                        + "Host nucleus {ECO:0000250|UniProtKB:P17763}. Note=Located in RE-associated vesicles hosting the "
                        + "replication complex. NS5 protein is mainly localized in the nucleus rather than in ER vesicles. "
                        + "{ECO:0000250|UniProtKB:P17763}.";

        assertEquals(expectedValue, value);
    }
}
