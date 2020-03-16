package org.uniprot.core.scorer.uniprotkb;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.UniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

class ProteinDescriptionScoredWithEvidencesTest {
    @Test
    void shouldDescriptionScore70() {
        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n"
                        + "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n"
                        + "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n";

        testDescription(deLine, 7.0, Consensus.COMPLEX, null);
    }

    @Test
    void shouldDescriptionWithHamapScore70() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n"
                        + "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n"
                        + "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n";

        testDescription(
                deLine, 7.0, Consensus.COMPLEX, singletonList(new EvidenceDatabase("HAMAP-Rule")));
    }

    @Test
    void shouldDescriptionWithMultiScore70() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n"
                        + "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n"
                        + "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n";

        testDescription(
                deLine,
                7.0,
                Consensus.COMPLEX,
                asList(new EvidenceDatabase("HAMAP-Rule"), new EvidenceDatabase("RuleBase")));
    }

    @Test
    void shouldDescriptionWithPIRNRScore50() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n"
                        + "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n"
                        + "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n";

        testDescription(
                deLine, 5.0, Consensus.COMPLEX, singletonList(new EvidenceDatabase("PIRNR")));
    }

    @Test
    void shouldDescriptionWithRuleBaseScore0() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n"
                        + "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n"
                        + "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n";

        testDescription(
                deLine, 0.0, Consensus.COMPLEX, singletonList(new EvidenceDatabase("RULEBASE")));
    }

    private void testDescription(
            String description,
            double score,
            Consensus consensus,
            List<EvidenceDatabase> evidences) {
        UniprotkbLineParserFactory parserFactory = new DefaultUniprotkbLineParserFactory();
        UniprotkbLineParser<DeLineObject> parser = parserFactory.createDeLineParser();
        DeLineConverter converter = new DeLineConverter();
        DeLineObject object = parser.parse(description);

        ProteinDescription newDescription = converter.convert(object);

        ProteinDescriptionScored scored = new ProteinDescriptionScored(newDescription, evidences);
        assertEquals(scored.score(), score, 0.0001);
        assertEquals(consensus, scored.consensus());
    }
}
