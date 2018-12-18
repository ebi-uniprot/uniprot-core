package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoEvidencesTest {

    @Test
    void testConvertGAFToECO() {
        //IBA	Default	ECO:0000318
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IBA");
        assertTrue(eco.isPresent());
        assertEquals("ECO:0000318", eco.get());
    }

    @Test
    void testConvertGAFToECODefault() {
        //IBA	Default	ECO:0000318
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IEA");
        assertTrue(eco.isPresent());
        assertEquals("ECO:0000501", eco.get());
    }

    @Test
    void testConvertGAFToECOStringString() {
        //IEA	GO_REF:0000037	ECO:0000322
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IEA", "GO_REF:0000037");
        assertTrue(eco.isPresent());
        assertEquals("ECO:0000322", eco.get());
    }

    @Test
    void testConvertECOToGAF() {
        Optional<String> gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000322");
        assertTrue(gaf.isPresent());
        assertEquals("IEA", gaf.get());

        gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000501");
        assertTrue(gaf.isPresent());
        assertEquals("IEA", gaf.get());

        gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000318");
        assertTrue(gaf.isPresent());
        assertEquals("IBA", gaf.get());
    }

}
