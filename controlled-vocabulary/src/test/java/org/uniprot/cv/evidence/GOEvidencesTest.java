package org.uniprot.cv.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GOEvidencesTest {
    private static final String WRONG = "WRONG";

    @Test
    void testConvertGAFToECO() {
        // IBA	Default	ECO:0000318
        Optional<String> eco = GOEvidences.INSTANCE.convertGAFToECO("IBA");
        assertEquals("ECO:0000318", eco.orElse(WRONG));
    }

    @Test
    void testConvertGAFToECODefault() {
        // IBA	Default	ECO:0000318
        Optional<String> eco = GOEvidences.INSTANCE.convertGAFToECO("IBA");
        assertEquals("ECO:0000318", eco.orElse(WRONG));
    }

    @Test
    void testConvertGAFToECOStringString() {
        // IEA	GO_REF:0000037	ECO:0000501
        Optional<String> eco = GOEvidences.INSTANCE.convertGAFToECO("IEA", "GO_REF:0000037");
        assertEquals("ECO:0000501", eco.orElse(WRONG));
    }

    @Test
    void testConvertECOToGAF() {
        Optional<String> gaf = GOEvidences.INSTANCE.convertECOToGAF("ECO:0000269");
        assertEquals("EXP", gaf.orElse(WRONG));

        gaf = GOEvidences.INSTANCE.convertECOToGAF("ECO:0000501");
        assertEquals("IEA", gaf.orElse(WRONG));

        gaf = GOEvidences.INSTANCE.convertECOToGAF("ECO:0000318");
        assertEquals("IBA", gaf.orElse(WRONG));
    }

    @ParameterizedTest(name = "[Code {0} exists?]")
    @MethodSource("provideAllGAFCode")
    void testGAFToECO(String gaf) {
        assertFalse(GOEvidences.INSTANCE.convertGAFToECO(gaf).isEmpty());
    }

    private static Stream<Arguments> provideAllGAFCode() {
        return List.of(
                        "EXP", "HDA", "HEP", "HGI", "HMP", "IBA", "IC", "IDA", "IEP", "IGC", "IGI",
                        "IMP", "IPI", "ISA", "ISM", "ISO", "ISS", "NAS", "TAS")
                .stream()
                .map(Arguments::of);
    }
}
