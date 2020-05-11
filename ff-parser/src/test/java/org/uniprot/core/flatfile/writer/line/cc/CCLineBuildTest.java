package org.uniprot.core.flatfile.writer.line.cc;

import org.junit.jupiter.api.Test;

class CCLineBuildTest extends CCBuildTestAbstr {

    @Test
    void testSIMILARITY3() {
        String ccLine =
                ("CC   -!- SIMILARITY: Belongs to the MHC class I family.\n"
                        + "CC   -!- SIMILARITY: Contains 1 Ig-like C1-type (immunoglobulin-like)"
                        + " domain.");
        doTest(ccLine);
    }

    @Test
    void testBigComment() {
        String ccLine =
                ("CC   -!- FUNCTION: Has immunoglobulin-binding and hemagglutination properties,\n"
                        + "CC       and can bind to mannose. Essential for virulence. May be involved"
                        + " in\n"
                        + "CC       LPS biosynthesis or polysaccharide transport.\n"
                        + "CC   -!- SUBCELLULAR LOCATION: Cell membrane; Single-pass membrane"
                        + " protein.\n"
                        + "CC   -!- DISRUPTION PHENOTYPE: Rough phenotype, with an aberrant"
                        + " O-antigen\n"
                        + "CC       profile. Mutants exhibit a reduced ability to colonize mouse"
                        + " spleens\n"
                        + "CC       but are still capable of producing a persistent infection, albeit"
                        + " with\n"
                        + "CC       a low bacterial burden.\n"
                        + "CC   -!- MISCELLANEOUS: Strongly immunoreactive, inducing both humoral"
                        + " and\n"
                        + "CC       cellular immune responses in hosts during the course of"
                        + " infection.\n"
                        + "CC   -!- SIMILARITY: Belongs to the BA14k family.");
        doTest(ccLine);
    }

    @Test
    void testBigCommentAgain() {
        String ccLine =
                ("CC   -!- FUNCTION: The B regulatory subunit may modulate substrate selectivity\n"
                        + "CC       and catalytic activity, and also may direct the localization of"
                        + " the\n"
                        + "CC       catalytic enzyme to a particular subcellular compartment (By\n"
                        + "CC       similarity).\n"
                        + "CC   -!- SUBUNIT: PP2A consists of a common heteromeric enzyme, composed"
                        + " of a\n"
                        + "CC       catalytic subunit (subunits C), a constant regulatory subunit"
                        + " (subunit\n"
                        + "CC       A), and a variety of regulatory subunits such as subunits B"
                        + " (the\n"
                        + "CC       R2/B/PR55/B55, R3/B''/PR72/PR130/PR59 and R5/B'/B56 families).\n"
                        + "CC   -!- TISSUE SPECIFICITY: Expressed ubiquitously at low levels.\n"
                        + "CC   -!- INDUCTION: By heat shock.\n"
                        + "CC   -!- SIMILARITY: Belongs to the phosphatase 2A regulatory subunit"
                        + " B56\n"
                        + "CC       family.");

        doTest(ccLine);
    }
}
