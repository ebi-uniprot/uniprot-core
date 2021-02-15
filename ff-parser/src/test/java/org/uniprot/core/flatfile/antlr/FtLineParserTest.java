package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTType;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;

@Slf4j
class FtLineParserTest {
    @Test
    void testChain() {
        String ftLines =
                "FT   CHAIN           20..873\n"
                        + "FT                   /note=\"104 kDa microneme/rhoptry antigen\"\n"
                        + "FT                   /id=\"PRO_0000232680\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.CHAIN,
                "20",
                "873",
                "104 kDa microneme/rhoptry antigen",
                "PRO_0000232680");
    }

    @Test
    void testHelix() {
        String ftLines = "FT   HELIX           33..83\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.HELIX, "33", "83", null, null);
    }

    @Test
    void testMutagenMultiLineText() {
        String ftLines =
                "FT   MUTAGEN         119\n"
                        + "FT                   /note=\"C->R,E,A: Loss of cADPr hydrolase and\n"
                        + "FT                   ADP-ribosyl cyclase activity\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.MUTAGEN,
                "119",
                "119",
                "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
                null);
    }

    @Test
    void testVarSeqMultiLineText() {
        String ftLines =
                "FT   VAR_SEQ         33..83\n"
                        + "FT                   /note=\"TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
                        + "FT                   DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform"
                        + " 2)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:17344846\"\n"
                        + "FT                   /id=\"VSP_004370\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.VAR_SEQ,
                "33",
                "83",
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP"
                        + " (in isoform 2)",
                "VSP_004370");
    }

    @Test
    void testVarSeqWraper1() {
        String ftLines =
                "FT   VAR_SEQ         33..83\n"
                        + "FT                   /note=\"TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
                        + "FT                   -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:17344846\"\n"
                        + "FT                   /id=\"VSP_004370\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.VAR_SEQ,
                "33",
                "83",
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL-> SECLTYGKQPLTSFHPFTSQMPP (in isoform"
                        + " 2)",
                "VSP_004370");
    }

    @Test
    void testVarSeq() {
        String ftLines =
                "FT   VAR_SEQ         1..31\n"
                        + "FT                   /note=\"MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR -> MPNKNK\n"
                        + "FT                   KEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTV\n"
                        + "FT                   SAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK (in isoform"
                        + " 4)\"\n"
                        + "FT                   /evidence=\"ECO:0000303|PubMed:14702039\"\n"
                        + "FT                   /id=\"VSP_043645\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        String desc =
                "MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR -> MPNKNKKEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTV"
                        + "SAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK(in isoform 4)";
        log.debug(desc);
        // verify(obj.getFts().get(0), FTType.VAR_SEQ, "1", "31",  desc, "VSP_043645");
        FtLineConverter converter = new FtLineConverter();
        List<UniProtKBFeature> features = converter.convert(obj);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(
                "MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR",
                feature.getAlternativeSequence().getOriginalSequence());

        assertEquals(
                "MPNKNKKEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTVSAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK",
                feature.getAlternativeSequence().getAlternativeSequences().get(0));
    }

    @Test
    void testVarSeq2() {
        String ftLines =
                "FT   VAR_SEQ         1\n"
                        + "FT                   /note=\"M -> MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPA\n"
                        + "FT                   SPSRRGSASRAGPGRASETM (in isoform L-VEGF-1)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT                   /id=\"VSP_038746\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        String desc =
                "M -> MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPA"
                        + "SPSRRGSASRAGPGRASETM (in isoform L-VEGF-1). {ECO:0000305}";
        log.debug(desc);
        // verify(obj.getFts().get(0), FTType.VAR_SEQ, "1", "31",  desc, "VSP_043645");
        FtLineConverter converter = new FtLineConverter();
        List<UniProtKBFeature> features = converter.convert(obj);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals("M", feature.getAlternativeSequence().getOriginalSequence());

        assertEquals(
                "MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPASPSRRGSASRAGPGRASETM",
                feature.getAlternativeSequence().getAlternativeSequences().get(0));
        assertEquals("in isoform L-VEGF-1", feature.getDescription().getValue());
    }

    @Test
    void testMultiFt() {
        String ftLines =
                "FT   VAR_SEQ         33..83\n"
                        + "FT                   /note=\"TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
                        + "FT                   DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform"
                        + " 2)\"\n"
                        + "FT                   /id=\"VSP_004370\"\n"
                        + "FT   MUTAGEN         119\n"
                        + "FT                   /note=\"C->R,E,A: Loss of cADPr hydrolase and\n"
                        + "FT                   ADP-ribosyl cyclase activity\"\n"
                        + "FT   HELIX           33..83\n"
                        + "FT   TURN            3..33\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(4, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.VAR_SEQ,
                "33",
                "83",
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP"
                        + " (in isoform 2)",
                "VSP_004370");
        verify(
                obj.getFts().get(1),
                FTType.MUTAGEN,
                "119",
                "119",
                "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
                null);
        verify(obj.getFts().get(2), FTType.HELIX, "33", "83", null, null);
        verify(obj.getFts().get(3), FTType.TURN, "3", "33", null, null);
    }

    @Test
    void testWithPotential() {
        String ftLines =
                "FT   CARBOHYD        61\n"
                        + "FT                   /note=\"N-linked (GlcNAc...); by host (Potential)\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.CARBOHYD,
                "61",
                "61",
                "N-linked (GlcNAc...); by host (Potential)",
                null);
    }

    @Test
    void testUnknown() {
        String ftLines =
                "FT   TRANSIT         1..?\n"
                        + "FT                   /note=\"Mitochondrion (Potential)\"\n"
                        + "FT   CHAIN           ?..610\n"
                        + "FT                   /note=\"Protein ABC1 homolog, mitochondrial\"\n"
                        + "FT                   /id=\"PRO_0000000261\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(2, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.TRANSIT, "1", "?", "Mitochondrion (Potential)", null);

        verify(
                obj.getFts().get(1),
                FTType.CHAIN,
                "?",
                "610",
                "Protein ABC1 homolog, mitochondrial",
                "PRO_0000000261");
    }

    @Test
    void testWithEvidence() {
        String ftLines =
                "FT   METAL           186\n"
                        + "FT                   /note=\"Calcium; via carbonyl oxygen\"\n"
                        + "FT                   /evidence=\"ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006|PubMed:23640942\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.METAL,
                "186",
                "186",
                "Calcium; via carbonyl oxygen",
                null);
        verifyEvidences(
                obj,
                obj.getFts().get(0),
                Arrays.asList(
                        new String[] {
                            "ECO:0000006|PubMed:20858735", "ECO:0000006|PubMed:23640942"
                        }));
    }

    @Test
    void testWithEvidence2() {
        String ftLines =
                "FT   HELIX           33..83\n"
                        + "FT                   /evidence=\"ECO:0000313|EMBL:BAG16761.1\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.HELIX, "33", "83", null, null);
        verifyEvidences(
                obj,
                obj.getFts().get(0),
                Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}));
    }

    @Test
    void testWithEvidence3() {
        String ftLines =
                "FT   REGION          237..240\n"
                        + "FT                   /note=\"Sulfate 1 binding\"\n"
                        + "FT   REGION          275..277\n"
                        + "FT                   /note=\"Phosphate 2 binding\"\n"
                        + "FT                   /evidence=\"ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(2, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.REGION, "237", "240", "Sulfate 1 binding", null);
        verifyEvidences(obj, obj.getFts().get(0), null);
        verify(obj.getFts().get(1), FTType.REGION, "275", "277", "Phosphate 2 binding", null);
        verifyEvidences(
                obj,
                obj.getFts().get(1),
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
    }

    @Test
    void testWithEvidence4() {
        String ftLines =
                "FT   TRANSMEM        57..77\n"
                        + "FT                   /note=\"Helical; (Potential)\"\n"
                        + "FT                   /evidence=\"ECO:0000257|HAMAP-Rule:MF_03021\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.TRANSMEM, "57", "77", "Helical; (Potential)", null);
        verifyEvidences(
                obj,
                obj.getFts().get(0),
                Arrays.asList(new String[] {"ECO:0000257|HAMAP-Rule:MF_03021"}));
    }

    @Test
    void testConflictFeature() {
        String ftLine =
                "FT   CONFLICT        1\n"
                        + "FT                   /note=\"A -> Q (in Ref. 1; BAA37160/BAA37165 and"
                        + " 2)\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FTType.CONFLICT,
                "1",
                "1",
                "A -> Q (in Ref. 1; BAA37160/BAA37165 and 2)",
                null);
        verifyEvidences(obj, obj.getFts().get(0), null);
    }

    @Test
    void testConflictFeature2() {
        String ftLine =
                "FT   CONFLICT        149..176\n"
                        + "FT                   /note=\"KREICYFQLYPDYIEQNIRSVRFNCYTK -> IERNMLLST\n"
                        + "FT                   VS (in Ref. 4; CAA78385)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        verify(
                obj.getFts().get(0),
                FTType.CONFLICT,
                "149",
                "176",
                "KREICYFQLYPDYIEQNIRSVRFNCYTK -> IERNMLLSTVS (in Ref. 4; CAA78385)",
                null);
        verifyEvidences(obj, obj.getFts().get(0), Arrays.asList("ECO:0000305"));
    }

    @Test
    void testConflictFeatureWithSlash() {
        String ftLine =
                "FT   CONFLICT        430..432\n"
                        + "FT                   /note=\"ALL -> DLV (in Ref. 1; BAA85929/BAA85930/\n"
                        + "FT                   BAA85931)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        verify(
                obj.getFts().get(0),
                FTType.CONFLICT,
                "430",
                "432",
                "ALL -> DLV (in Ref. 1; BAA85929/BAA85930/BAA85931)",
                null);
        verifyEvidences(obj, obj.getFts().get(0), Arrays.asList("ECO:0000305"));
    }

    @Test
    void testVariantWithMulti() {
        String ftLine =
                "FT   VARIANT         267..294\n"
                        + "FT                   /note=\"ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n"
                        + "FT                   LKVPVVQKVTKRLGVTSPD\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        verify(
                obj.getFts().get(0),
                FTType.VARIANT,
                "267",
                "294",
                "ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD",
                null);
    }

    @Test
    void testVariantWithMulti2() {
        String ftLine =
                "FT   VARIANT         157..224\n"
                        + "FT                   /note=\"EGKGLSLPLDSFSVRLHQDGQVSIELPDSHSPCYIKTYEVD\n"
                        + "FT                   PGYKMAVCAAHPDFPEDITMVSYEELL -> GRQRLIASA\n"
                        + "FT                   (in strain 168 and its derivatives, non"
                        + " surfactin-producing strains)\"\n";

        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        verify(
                obj.getFts().get(0),
                FTType.VARIANT,
                "157",
                "224",
                "EGKGLSLPLDSFSVRLHQDGQVSIELPDSHSPCYIKTYEVDPGYKMAVCAAHPDFPEDITMVSYEELL -> GRQRLIASA"
                        + " (in strain 168 and its derivatives, non surfactin-producing strains)",
                null);
    }

    @Test
    void testVarSeqWithMulti() {
        String ftLine =
                "FT   VAR_SEQ         267..294\n"
                        + "FT                   /note=\"ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n"
                        + "FT                   LKVPVVQKVTKRLGVTSPD\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        assertEquals(1, obj.getFts().size());
        log.debug(obj.getFts().get(0).getFtText());
        verify(
                obj.getFts().get(0),
                FTType.VAR_SEQ,
                "267",
                "294",
                "ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD",
                null);
    }

    @Test
    void testMultiFeatures() {
        String ftLines =
                "FT   SIGNAL          1..22\n"
                        + "FT                   /evidence=\"ECO:0000255\"\n"
                        + "FT   PROPEP          23..36\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:3758080\"\n"
                        + "FT                   /id=\"PRO_0000032105\"\n"
                        + "FT   CHAIN           37..64\n"
                        + "FT                   /note=\"2S sulfur-rich seed storage protein small"
                        + " chain 1\"\n"
                        + "FT                   /id=\"PRO_0000032106\"\n"
                        + "FT   PROPEP          65..69\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:3758080\"\n"
                        + "FT                   /id=\"PRO_0000032107\"\n"
                        + "FT   CHAIN           70..142\n"
                        + "FT                   /note=\"2S sulfur-rich seed storage protein large"
                        + " chain 1B\"\n"
                        + "FT                   /id=\"PRO_0000032108\"\n"
                        + "FT   PROPEP          143..146\n"
                        + "FT                   /id=\"PRO_0000032109\"\n"
                        + "FT   MOD_RES         37\n"
                        + "FT                   /note=\"Pyrrolidone carboxylic acid\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:3758080\"\n"
                        + "FT   DISULFID        40..92\n"
                        + "FT                   /note=\"Interchain (between small and large"
                        + " chains)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:12421566\"\n"
                        + "FT   DISULFID        53..81\n"
                        + "FT                   /note=\"Interchain (between small and large"
                        + " chains)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:12421566\"\n"
                        + "FT   DISULFID        82..130\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:12421566\"\n"
                        + "FT   DISULFID        94..137\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:12421566\"\n"
                        + "FT   VARIANT         91\n"
                        + "FT                   /note=\"S -> E (in variant 1A)\"\n"
                        + "FT   CONFLICT        38..39\n"
                        + "FT                   /note=\"EE -> QQ (in Ref. 5; AA sequence)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT   CONFLICT        102..103\n"
                        + "FT                   /note=\"MR -> RM (in Ref. 4; BAA96554)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT   CONFLICT        107\n"
                        + "FT                   /note=\"E -> K (in Ref. 4; BAA96554)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT   CONFLICT        122\n"
                        + "FT                   /note=\"L -> M (in Ref. 5; AA sequence)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT   CONFLICT        126\n"
                        + "FT                   /note=\"I -> L (in Ref. 5; AA sequence)\"\n"
                        + "FT                   /evidence=\"ECO:0000305\"\n"
                        + "FT   HELIX           37..46\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   HELIX           49..62\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   TURN            63..66\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   STRAND          71..73\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   HELIX           76..87\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   HELIX           90..108\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   HELIX           114..130\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n"
                        + "FT   TURN            138..141\n"
                        + "FT                   /evidence=\"ECO:0000244|PDB:2LVF\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(25, obj.getFts().size());
    }

    private void verify(
            FtLineObject.FT ft,
            FTType type,
            String start,
            String end,
            String description,
            String ftid) {
        assertEquals(type, ft.getType());
        assertEquals(start, ft.getLocationStart());
        assertEquals(end, ft.getLocationEnd());
        assertEquals(description, ft.getFtText());
        assertEquals(ftid, ft.getFtId());
    }

    private void verifyEvidences(FtLineObject obj, Object name, List<String> evidences) {
        List<String> expected = obj.getEvidenceInfo().getEvidences().get(name);
        assertEquals(expected, evidences);
    }
}
