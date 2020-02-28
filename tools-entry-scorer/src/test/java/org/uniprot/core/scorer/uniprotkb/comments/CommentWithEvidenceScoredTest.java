package org.uniprot.core.scorer.uniprotkb.comments;

import static java.util.Collections.singletonList;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;

class CommentWithEvidenceScoredTest extends CommentScoreTestBase {
    @Test
    void hamapShouldScore30() {
        String line =
                "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n"
                        + "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}.";
        verify(CommentType.SUBUNIT, line, 3.0, singletonList(new EvidenceDatabase("HAMAP-Rule")));
    }

    @Test
    void saasShouldScore30() {
        String line =
                "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n"
                        + "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}.";
        verify(CommentType.SUBUNIT, line, 3.0, singletonList(new EvidenceDatabase("SAAS")));
    }

    @Test
    void ruleBaseShouldScore0() {
        String line =
                "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n"
                        + "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}.";
        verify(CommentType.SUBUNIT, line, 0.0, singletonList(new EvidenceDatabase("RULEBASE")));
    }

    @Test
    void hamapShouldScore10() {
        String line =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}.";

        verify(
                CommentType.SUBCELLULAR_LOCATION,
                line,
                1.0,
                singletonList(new EvidenceDatabase("HAMAP-Rule")));
    }

    @Test
    void pirnrShouldScore10() {
        String line =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}.";

        verify(
                CommentType.SUBCELLULAR_LOCATION,
                line,
                1.0,
                singletonList(new EvidenceDatabase("PIRNR")));
    }

    @Test
    void rulebaseShouldScore00() {
        String line =
                "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n"
                        + "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}.";

        verify(
                CommentType.SUBCELLULAR_LOCATION,
                line,
                0.0,
                singletonList(new EvidenceDatabase("RULEBASE")));
    }
}
