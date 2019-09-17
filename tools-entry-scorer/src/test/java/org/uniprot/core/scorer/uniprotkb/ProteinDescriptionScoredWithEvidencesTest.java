package org.uniprot.core.scorer.uniprotkb;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.UniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.ProteinDescriptionScored;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.evidence.EvidenceType;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProteinDescriptionScoredWithEvidencesTest {
    @Test
    void shouldDescriptionScore70() {
        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n" +
                        "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n" +
                        "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n" ;



        testDescription(deLine, 7.0, Consensus.COMPLEX, null);
    }

    @Test
    void shouldDescriptionWithHamapScore70() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n" +
                        "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n" +
                        "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n" ;

        testDescription(deLine, 7.0, Consensus.COMPLEX, singletonList(new EvidenceType("HAMAP-Rule")));
    }

    @Test
    void shouldDescriptionWithMultiScore70() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n" +
                        "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n" +
                        "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n" ;

        testDescription(deLine, 7.0, Consensus.COMPLEX, asList(new EvidenceType("HAMAP-Rule"), new EvidenceType("RuleBase")));
    }

    @Test
    void shouldDescriptionWithPIRNRScore50() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n" +
                        "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n" +
                        "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n" ;

        testDescription(deLine, 5.0, Consensus.COMPLEX, singletonList(new EvidenceType("PIRNR")));
    }

    @Test
    void shouldDescriptionWithRuleBaseScore0() {

        String deLine =
                "DE   RecName: Full=Eukaryotic translation initiation factor 3 subunit E {ECO:0000256|HAMAP-Rule:MF_03004, ECO:0000256|PIRNR:PIRNR016255};\n" +
                        "DE            Short=eIF3e {ECO:0000256|HAMAP-Rule:MF_03004};\n" +
                        "DE   AltName: Full=Eukaryotic translation initiation factor 3 subunit 6 {ECO:0000256|HAMAP-Rule:MF_03004};\n" ;

        testDescription(deLine, 0.0, Consensus.COMPLEX, singletonList(new EvidenceType("RULEBASE")));
    }

    private void testDescription(String description, double score, Consensus consensus, List<EvidenceType> evidences){
        UniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();
        UniprotLineParser<DeLineObject> parser =parserFactory.createDeLineParser();
        DeLineConverter converter = new DeLineConverter();
        DeLineObject object =parser.parse(description);


        ProteinDescription newDescription = converter.convert(object);

        ProteinDescriptionScored scored = new ProteinDescriptionScored (newDescription, evidences);
        assertEquals(scored.score(), score, 0.0001);
        assertEquals(consensus,scored.consensus());
    }
}