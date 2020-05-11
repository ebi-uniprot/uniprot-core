package org.uniprot.core.scorer.uniprotkb.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.UniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineTransformer;
import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.features.FeatureScored;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FeatureScoredTest {

    private static final FtLineTransformer TRANSFORMER = new FtLineTransformer();

    @Test
    void shouldModResScore3() throws Exception {
        String line =
                "FT   MOD_RES         87\n"
                        + "FT                   /note=\"Phosphoserine\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_01146\"\n";

        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldActiveSiteScore9() throws Exception {
        String line =
                "FT   ACT_SITE        79..197\n"
                        + "FT                   /note=\"Response regulatory\"\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    void shouldCarbohydScore3() throws Exception {
        String line =
                "FT   CARBOHYD        100\n"
                        + "FT                   /note=\"O-linked (Gal...)\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_01146\"\n";

        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldChainScore1() throws Exception {
        String line =
                "FT   CHAIN           61..386\n"
                        + "FT                   /note=\"ATP synthase gamma chain 2\"\n"
                        + "FT                   /id=\"PRO_0000002675\"\n";

        testFeatureScore(line, 1.0);
    }

    @Test
    void shouldCoiledScore3() throws Exception {
        String line =
                "FT   COILED          104..217\n"
                        + "FT                   /evidence=\"ECO:0000256\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldCompbiasScore3() throws Exception {
        String line =
                "FT   COMPBIAS        17..96\n"
                        + "FT                   /note=\"Asp-rich (acidic)\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldConflictScore0() throws Exception {
        String line =
                "FT   CONFLICT        3..4\n"
                        + "FT                   /note=\"CD -> VW (in Ref. 3)\"\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldCrosslinkScore9() throws Exception {
        String line =
                "FT   CROSSLNK        83\n"
                        + "FT                   /note=\"Glycyl lysine isopeptide (Gly-Lys) (with"
                        + " M-243)\"\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    void shouldDnaBindScore3() throws Exception {
        String line =
                "FT   DNA_BIND        54..77\n"
                        + "FT                   /note=\"H-T-H motif\"\n"
                        + "FT                   /evidence=\"ECO:0000256\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldDomainScore3() throws Exception {
        String line =
                "FT   DOMAIN          106..>131\n" + "FT                   /note=\"EF-hand 4\"\n";

        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldHelixScore0() throws Exception {
        String line = "FT   HELIX           710..736\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldInitMetScore9() throws Exception {
        String line =
                "FT   INIT_MET        50\n"
                        + "FT                   /note=\"For isoform HLF36 and isoform HLF17\"\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    void shouldLipidScore3() throws Exception {
        String line =
                "FT   LIPID           1\n" + "FT                   /note=\"N-myristoyl glycine\"\n";

        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldMetalScore3() throws Exception {
        String line =
                "FT   METAL           96\n"
                        + "FT                   /note=\"Cobalt 1 and 2\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_01146\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldMotifScore1() throws Exception {
        String line =
                "FT   MOTIF           50..58\n"
                        + "FT                   /note=\"Effector region\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_01146\"\n";
        testFeatureScore(line, 1.0);
    }

    @Test
    void shouldMutagenScore3() throws Exception {
        String line =
                "FT   MUTAGEN         2\n" + "FT                   /note=\"B->A: No activity\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldNPBindScore3() throws Exception {
        String line =
                "FT   NP_BIND         9..14\n"
                        + "FT                   /note=\"FAD (ADP part)\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_01146\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldPeptideScore1() throws Exception {
        String line =
                "FT   PEPTIDE         110..123\n" + "FT                   /note=\"Urotensin-2\"\n";

        testFeatureScore(line, 1.0);
    }

    @Test
    void shouldPropepScore3() throws Exception {
        String line = "FT   PROPEP          20..37\n";

        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldTopoDomScore3() throws Exception {
        String line =
                "FT   TOPO_DOM        164..176\n"
                        + "FT                   /note=\"Cytoplasmic\"\n"
                        + "FT                   /evidence=\"ECO:0000256\"\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    void shouldVariantScore2() throws Exception {
        String line =
                "FT   VARIANT         3\n"
                        + "FT                   /note=\"C -> H (in homocystinuria; mild form)\"\n";
        testFeatureScore(line, 2.0);
    }

    private double scoreList(List<HasScore> objects) {
        double thisScore = 0;
        for (HasScore scored : objects) {
            if (scored.consensus() == Consensus.PRESENCE)
                thisScore = scored.score() > thisScore ? scored.score() : thisScore;
            else thisScore += scored.score();
        }
        return thisScore;
    }

    @Test
    void shouldVarSeqScore0() throws Exception {
        String line =
                "FT   VAR_SEQ         1..55\n"
                        + "FT                   /note=\"Missing (in isoform 4)\"\n"
                        + "FT                   /id=\"VSP_005610\"\n";

        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldStrandScore0() throws Exception {
        String line = "FT   STRAND          465..466\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldTurnScore0() throws Exception {
        String line = "FT   TURN            465..466\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldNonConsScore0() throws Exception {
        String line = "FT   NON_CONS        465..466\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    void shouldIntramemScore3() throws Exception {
        String line =
                "FT   INTRAMEM        242..261\n"
                        + "FT                   /note=\"Pore-forming; Name=Segment H5\"\n"
                        + "FT                   /evidence=\"ECO:0000256\"\n";
        testFeatureScore(line, 3.0);
    }

    private void testFeatureScore(String featureLines, double expectedScore) throws Exception {
        UniprotKBLineParserFactory parserFactory = new DefaultUniprotKBLineParserFactory();
        UniprotKBLineParser<FtLineObject> parser = parserFactory.createFtLineParser();
        FtLineObject obj = parser.parse(featureLines);
        FtLineConverter converter = new FtLineConverter();
        List<UniProtKBFeature> features = converter.convert(obj);

        double score = 0;
        for (UniprotKBFeatureType type : UniprotKBFeatureType.values()) {
            List<HasScore> scoredList = new ArrayList<>();
            for (UniProtKBFeature feature :
                    features.stream()
                            .filter(f -> f.getType().equals(type))
                            .collect(Collectors.toList())) {
                FeatureScored scored = new FeatureScored(feature);
                scored.setIsSwissProt(true);
                scoredList.add(scored);
            }

            score += this.scoreList(scoredList);
        }

        assertEquals(score, expectedScore, 0.001);
    }
}
