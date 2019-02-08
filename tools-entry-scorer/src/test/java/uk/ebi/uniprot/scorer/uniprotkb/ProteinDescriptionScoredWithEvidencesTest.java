package uk.ebi.uniprot.scorer.uniprotkb;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

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