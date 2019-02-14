package uk.ebi.uniprot.scorer.uniprotkb.feature;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineTransformer;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.features.FeatureScored;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FeatureScoredTest {

    public static final FtLineTransformer TRANSFORMER = new FtLineTransformer();

    @Test
    public void shouldModResScore3() throws Exception {
        String line = "FT   MOD_RES      87     87       Phosphoserine. {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldActiveSiteScore9() throws Exception {
        String line = "FT   ACT_SITE       79    197       Response regulatory.\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    public void shouldCarbohydScore3() throws Exception {
        String line = "FT   CARBOHYD    100    100       O-linked (Gal...). {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldChainScore1() throws Exception {
        String line = "FT   CHAIN        61    386       ATP synthase gamma chain 2.\n" +
                "FT                                /FTId=PRO_0000002675.\n";
        testFeatureScore(line, 1.0);
    }

    @Test
    public void shouldCoiledScore3() throws Exception {
        String line = "FT   COILED      104    217       {ECO:0000256}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldCompbiasScore3() throws Exception {
        String line = "FT   COMPBIAS     17     96       Asp-rich (acidic).\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldConflictScore0() throws Exception {
        String line = "FT   CONFLICT     3     4       CD -> VW (in Ref. 3).\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    public void shouldCrosslinkScore9() throws Exception {
        String line = "FT   CROSSLNK     83     83       Glycyl lysine isopeptide (Gly-Lys)\n" +
                "FT                                (with M-243).\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    public void shouldDnaBindScore3() throws Exception {
        String line = "FT   DNA_BIND     54     77       H-T-H motif. {ECO:0000256}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldDomainScore3() throws Exception {
        String line = "FT   DOMAIN      106   >131       EF-hand 4.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldHelixScore0() throws Exception {
        String line = "FT   HELIX       710    736\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    public void shouldInitMetScore9() throws Exception {
        String line = "FT   INIT_MET     50     50       For isoform HLF36 and isoform HLF17.\n";
        testFeatureScore(line, 9.0);
    }

    @Test
    public void shouldLipidScore3() throws Exception {
        String line = "FT   LIPID         1      1       N-myristoyl glycine.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldMetalScore3() throws Exception {
        String line = "FT   METAL        96     96       Cobalt 1 and 2. {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldMotifScore1() throws Exception {
        String line = "FT   MOTIF        50     58       Effector region. {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        testFeatureScore(line, 1.0);
    }

    @Test
    public void shouldMutagenScore3() throws Exception {
        String line = "FT   MUTAGEN     2    2       B->A: No activity.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldNPBindScore3() throws Exception {
        String line = "FT   NP_BIND       9     14       FAD (ADP part). {ECO:0000256|HAMAP-Rule:MF_01146}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldPeptideScore1() throws Exception {
        String line = "FT   PEPTIDE     110    123       Urotensin-2.\n";
        testFeatureScore(line, 1.0);
    }

    @Test
    public void shouldPropepScore3() throws Exception {
        String line = "FT   PROPEP       20     37\n";
        testFeatureScore(line, 3.0);

    }

    @Test
    public void shouldTopoDomScore3() throws Exception {
        String line = "FT   TOPO_DOM    164    176       Cytoplasmic. {ECO:0000256}.\n";
        testFeatureScore(line, 3.0);
    }

    @Test
    public void shouldVariantScore2() throws Exception {
        String line = "FT   VARIANT     3    3       C -> H (in homocystinuria; mild form).\n";
        testFeatureScore(line, 2.0);
    }

    @Test
    public void shouldVariantScore24() throws Exception {
        String line = "FT   VARIANT      48     48       A -> S (in allele B*1503).\n" +
                "FT                                /FTId=VAR_016365.\n" +
                "FT   VARIANT      69     70       MA -> EE (in allele B*1503).\n" +
                "FT                                /FTId=VAR_016366.\n" +
                "FT   VARIANT      87     87       E -> N (in allele B*1502 and allele\n" +
                "FT                                B*1511; requires 2 nucleotide\n" +
                "FT                                substitutions).\n" +
                "FT                                /FTId=VAR_016367.\n" +
                "FT   VARIANT      91     91       S -> C (in allele B*1566).\n" +
                "FT                                /FTId=VAR_016368.\n" +
                "FT   VARIANT      91     91       S -> Y (in allele B*1511).\n" +
                "FT                                /FTId=VAR_016369.\n" +
                "FT   VARIANT     118    119       TL -> II (in allele B*1502).\n" +
                "FT                                /FTId=VAR_016370.\n" +
                "FT   VARIANT     119    119       L -> W (in allele B*1504).\n" +
                "FT                                /FTId=VAR_016371.\n" +
                "FT   VARIANT     121    121       R -> T (in allele B*1504).\n" +
                "FT                                /FTId=VAR_016372.\n" +
                "FT   VARIANT     137    137       H -> Y (in allele B*1502).\n" +
                "FT                                /FTId=VAR_016373.\n" +
                "FT   VARIANT     180    180       W -> L (in allele B*1502 and allele\n" +
                "FT                                B*1503).\n" +
                "FT                                /FTId=VAR_016374.\n" +
                "FT   VARIANT     190    191       EW -> DG (in allele B*1519).\n" +
                "FT                                /FTId=VAR_016375.\n" +
                "FT   VARIANT     274    274       P -> L (in allele B*1519).\n" +
                "FT                                /FTId=VAR_016376.\n";
        testFeatureScore(line, 24.0);

    }

    private double scoreList(List<HasScore> objects) {
        double thisScore = 0;
        for (HasScore scored : objects) {
            if (scored.consensus() == Consensus.PRESENCE)
                thisScore = scored.score() > thisScore ? scored.score() : thisScore;
            else
                thisScore += scored.score();
        }
        return thisScore;
    }

    @Test
    public void shouldVarSeqScore0() throws Exception {
        String line = "FT   VAR_SEQ      1     55       Missing (in isoform 4).\n" +
                "FT                                /FTId=VSP_005610.\n";
        testFeatureScore(line, 0.0);

    }

    @Test
    public void shouldStrandScore0() throws Exception {
        String line = "FT   STRAND      465    466\n";
        testFeatureScore(line, 0.0);
    }

    @Test
    public void shouldTurnScore0() throws Exception {
        String line = "FT   TURN      465    466\n";
        testFeatureScore(line, 0.0);

    }

    @Test
    public void shouldNonConsScore0() throws Exception {
        String line = "FT   NON_CONS      465    466\n";
        testFeatureScore(line, 0.0);
    }

    @Ignore
    @Test
    public void shouldNonTerScore0() throws Exception {
        List<Feature> features = TRANSFORMER.transformNoHeader("FT   NON_TER      465    466\n");

        assertEquals(features.size(), 1);

        FeatureScored scored = new FeatureScored(features.get(0));

        assertEquals(scored.score(), 0.0, 0.001);
    }

    @Test
    public void shouldIntramemScore3() throws Exception {
        String line = "FT   INTRAMEM    242    261       Pore-forming; Name=Segment H5.\n" +
                "FT                                {ECO:0000256}.\n";
        testFeatureScore(line, 3.0);

    }

    @Ignore
    @Test
    public void shouldIntramem2Score3() throws Exception {
        List<Feature> features = TRANSFORMER
                .transformNoHeader("FT   INTRAMEM    108    115       {ECO:0000256|HAMAP-Rule:MF_01146}.\n");

        assertEquals(features.size(), 1);

        FeatureScored scored = new FeatureScored(features.get(0));

        assertEquals(scored.score(), 3.0, 0.001);
    }

    @Test
    public void shouldTestScore26() throws Exception {
        String line = "FT   INIT_MET      1      1       Removed. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   CHAIN         2    271       Aquaporin-1.\n" +
                "FT                                /FTId=PRO_0000063919.\n" +
                "FT   TOPO_DOM      2      9       Cytoplasmic. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM     10     34       Helical; Name=Helix 1. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM     35     50       Extracellular. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM     51     68       Helical; Name=Helix 2. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM     69     73       Cytoplasmic. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   INTRAMEM     74     78\n" +
                "FT   INTRAMEM     79     88       Helical; Name=Helix B. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM     89     92       Cytoplasmic. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM     93    117       Helical; Name=Helix 3. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM    118    142       Extracellular. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM    143    158       Helical; Name=Helix 4. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM    159    169       Cytoplasmic. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM    170    187       Helical; Name=Helix 5. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM    188    189       Extracellular. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   INTRAMEM    190    193\n" +
                "FT   INTRAMEM    194    204       Helical; Name=Helix E. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM    205    215       Extracellular. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TRANSMEM    216    230       Helical; Name=Helix 6. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   TOPO_DOM    231    271       Cytoplasmic. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   MOTIF        78     80       NPA 1.\n" +
                "FT   MOTIF       194    196       NPA 2.\n" +
                "FT   COMPBIAS    161    164       Poly-Arg.\n" +
                "FT   SITE         58     58       Substrate discrimination. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   SITE        182    182       Substrate discrimination. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   SITE        191    191       Hg(2+)-sensitive residue. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   SITE        197    197       Substrate discrimination. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   MOD_RES     248    248       Phosphothreonine. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   MOD_RES     249    249       Phosphoserine. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   MOD_RES     264    264       Phosphoserine. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "FT   CARBOHYD     42     42       N-linked (GlcNAc...). {ECO:0000256}.\n";

        testFeatureScore(line, 26.0);

    }

    private void testFeatureScore(String featureLines, double expectedScore) throws Exception {
        UniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();
        UniprotLineParser<FtLineObject> parser = parserFactory.createFtLineParser();
        FtLineObject obj = parser.parse(featureLines);
        FtLineConverter converter = new FtLineConverter();
        List<Feature> features = converter.convert(obj);

        double score = 0;
        for (FeatureType type : FeatureType.values()) {
            List<HasScore> scoredList = new ArrayList<>();
            for (Feature feature : features.stream()
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