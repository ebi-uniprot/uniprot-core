package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidenceType;

import static java.util.Collections.singletonList;

public class CommentWithEvidenceScoredTest extends CommentScoreTestBase {
    @Test
    public void hamapShouldScore30() throws Exception {
        String line = "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n" +
                "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
                "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}." ;
        verify(CommentType.SUBUNIT, line, 3.0, singletonList(new EvidenceType("HAMAP-Rule")));
    }
    @Test
    public void saasShouldScore30() throws Exception {
        String line = "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n" +
                "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
                "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}." ;
        verify(CommentType.SUBUNIT, line, 3.0, singletonList(new EvidenceType("SAAS")));
    }

    @Test
    public void ruleBaseShouldScore0() throws Exception {
        String line = "CC   -!- SUBUNIT: Component of the eukaryotic translation initiation factor\n" +
                "CC       3 (eIF-3) complex. {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
                "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00549133}." ;
        verify(CommentType.SUBUNIT, line, 0.0, singletonList(new EvidenceType("RULEBASE")));
    }
    @Test
    public void hamapShouldScore10() throws Exception {
        String line = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
        "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}." ;

        verify(CommentType.SUBCELLULAR_LOCATION, line, 1.0, singletonList(new EvidenceType("HAMAP-Rule")));
    }
    
    @Test
    public void pirnrShouldScore10() throws Exception {
        String line = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
        "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}." ;

        verify(CommentType.SUBCELLULAR_LOCATION, line, 1.0, singletonList(new EvidenceType("PIRNR")));
    }
    
    @Test
    public void rulebaseShouldScore00() throws Exception {
        String line = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_03004,\n" +
        "CC       ECO:0000256|PIRNR:PIRNR016255, ECO:0000256|SAAS:SAAS00036514}." ;

        verify(CommentType.SUBCELLULAR_LOCATION, line, 0.0,singletonList(new EvidenceType("RULEBASE")));
    }
}
