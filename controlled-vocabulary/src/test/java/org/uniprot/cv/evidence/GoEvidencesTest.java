package org.uniprot.cv.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class GoEvidencesTest {
    private static final String WRONG = "WRONG";

    @Test
    void testConvertGAFToECO() {
        // IBA	Default	ECO:0000318
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IBA");
        assertEquals("ECO:0000318", eco.orElse(WRONG));
    }

    @Test
    void testConvertGAFToECODefault() {
        // IBA	Default	ECO:0000318
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IEA");
        assertEquals("ECO:0000501", eco.orElse(WRONG));
    }

    @Test
    void testConvertGAFToECOStringString() {
        // IEA	GO_REF:0000037	ECO:0000322
        Optional<String> eco = GoEvidences.INSTANCE.convertGAFToECO("IEA", "GO_REF:0000037");
        assertEquals("ECO:0000322", eco.orElse(WRONG));
    }

    @Test
    void testConvertECOToGAF() {
        Optional<String> gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000322");
        assertEquals("IEA", gaf.orElse(WRONG));

        gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000501");
        assertEquals("IEA", gaf.orElse(WRONG));

        gaf = GoEvidences.INSTANCE.convertECOToGAF("ECO:0000318");
        assertEquals("IBA", gaf.orElse(WRONG));
    }
}
