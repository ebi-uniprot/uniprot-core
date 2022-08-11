package org.uniprot.core.flatfile.writer.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FTVasplicFeatureBuildTest extends FTBuildTestAbstr {
    @Test
    void testVarsplic() {

        String ftLine =
                "FT   VAR_SEQ         167..229\n"
                        + "FT                   /note=\"Missing (in isoform Alpha and isoform"
                        + " Beta)\"\n"
                        + "FT                   /id=\"VSP_005610\"";
        String ftLineString =
                "VAR_SEQ 167..229\n"
                        + "/note=\"Missing (in isoform Alpha and isoform Beta)\"\n"
                        + "/id=\"VSP_005610\"";

        String originalSequence = null;
        List<String> alternativeSequences = new ArrayList<>();
        // alternativeSequences.add("E");
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Alpha");
        report.add("Beta");
        FeatureLocation location = new FeatureLocation(167, 229);
        String featureId = "VSP_005610";
        String description = "in isoform Alpha and isoform Beta";
        List<String> evs = new ArrayList<>();
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testVarsplicEvidence() {

        String ftLine =
                "FT   VAR_SEQ         167..229\n"
                        + "FT                   /note=\"Missing (in isoform Alpha and isoform"
                        + " Beta)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                        + "FT                   ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "FT                   /id=\"VSP_005610\"";
        String ftLineStringEv =
                "VAR_SEQ 167..229\n"
                        + "/note=\"Missing (in isoform Alpha and isoform Beta)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "/id=\"VSP_005610\"";
        String ftLineString =
                "VAR_SEQ 167..229\n"
                        + "/note=\"Missing (in isoform Alpha and isoform Beta)\"\n"
                        + "/id=\"VSP_005610\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";

        String originalSequence = null;
        List<String> alternativeSequences = new ArrayList<>();
        // alternativeSequences.add("E");
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Alpha");
        report.add("Beta");
        FeatureLocation location = new FeatureLocation(167, 229);
        String featureId = "VSP_005610";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        String description = "in isoform Alpha and isoform Beta";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    void testVarsplicMulti() {
        String ftLine =
                "FT   VAR_SEQ         46\n"
                        + "FT                   /note=\"R -> MLW,RRKI,GPQMTLSHA (in isoform Long)\"\n"
                        + "FT                   /id=\"VSP_005610\"";
        String ftLineString =
                "VAR_SEQ 46\n"
                        + "/note=\"R -> MLW,RRKI,GPQMTLSHA (in isoform Long)\"\n"
                        + "/id=\"VSP_005610\"";

        String originalSequence = "R";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("MLW");
        alternativeSequences.add("RRKI");
        alternativeSequences.add("GPQMTLSHA");
        List<String> report = new ArrayList<>();

        report.add("Long");
        // report.add("Beta");
        FeatureLocation location = new FeatureLocation(46, 46);
        String featureId = "VSP_005610";

        List<String> evs = new ArrayList<>();
        // evs.add(ev1);
        // evs.add(ev2);
        String description = "in isoform Long";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testVarsplic2() {
        String ftLine =
                "FT   VAR_SEQ         46\n"
                        + "FT                   /note=\"R -> MLWRRKIGPQMTLSHAAG (in isoform Long)\"\n"
                        + "FT                   /id=\"VSP_005610\"";
        String ftLineString =
                "VAR_SEQ 46\n"
                        + "/note=\"R -> MLWRRKIGPQMTLSHAAG (in isoform Long)\"\n"
                        + "/id=\"VSP_005610\"";

        String originalSequence = "R";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("MLWRRKIGPQMTLSHAAG");
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Long");
        // report.add("Beta");
        FeatureLocation location = new FeatureLocation(46, 46);
        String featureId = "VSP_005610";

        List<String> evs = new ArrayList<>();
        // evs.add(ev1);
        // evs.add(ev2);
        String description = "in isoform Long";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testVarsplic2Evidence() {
        String ftLine =
                "FT   VAR_SEQ         46\n"
                        + "FT                   /note=\"R -> MLWRRKIGPQMTLSHAAG (in isoform Long)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                        + "FT                   ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "FT                   /id=\"VSP_005610\"";
        String ftLineString =
                "VAR_SEQ 46\n"
                        + "/note=\"R -> MLWRRKIGPQMTLSHAAG (in isoform Long)\"\n"
                        + "/id=\"VSP_005610\"";
        String ftLineStringEv =
                "VAR_SEQ 46\n"
                        + "/note=\"R -> MLWRRKIGPQMTLSHAAG (in isoform Long)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "/id=\"VSP_005610\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";

        String originalSequence = "R";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("MLWRRKIGPQMTLSHAAG");
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Long");
        // report.add("Beta");
        FeatureLocation location = new FeatureLocation(46, 46);
        String featureId = "VSP_005610";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        String description = "in isoform Long";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    void testVarsplic3() {
        String ftLine =
                "FT   VAR_SEQ         103..222\n"
                        + "FT                  "
                        + " /note=\"GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPY\n"
                        + "FT                  "
                        + " PYSKKLAEKAVLAANGWNLKNGDTLYTCALRPTYIYGEGGPFLSASINEALNNNGILSS\n"
                        + "FT                   VGK ->"
                        + " FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVFGVTHRQ\n"
                        + "FT                  "
                        + " SIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSLSIEHLLCSGPSDFADHYQLGELKA\n"
                        + "FT                   AIFSFIDEKTRTEQ (in isoform 2)\"\n"
                        + "FT                   /id=\"VSP_037399\"";
        String ftLineString =
                "VAR_SEQ 103..222\n"
                        + "/note=\"GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCALRPTYIYGEGGPFLSASINEALNNNGILSSVGK"
                        + " -> FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVFGVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSLSIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ"
                        + " (in isoform 2)\"\n"
                        + "/id=\"VSP_037399\"";

        String originalSequence =
                "GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL"
                        + "RPTYIYGEGGPFLSASINEALNNNGILSSVGK";
        String alt =
                "FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF"
                        + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
                        + "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add(alt);
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("2");
        // report.add("Beta");
        FeatureLocation location = new FeatureLocation(103, 222);
        String featureId = "VSP_037399";

        List<String> evs = new ArrayList<>();
        // evs.add(ev1);
        // evs.add(ev2);
        String description = "in isoform 2";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testVarsplic3Evidence() {
        String ftLine =
                "FT   VAR_SEQ         103..222\n"
                        + "FT                  "
                        + " /note=\"GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPY\n"
                        + "FT                  "
                        + " PYSKKLAEKAVLAANGWNLKNGDTLYTCALRPTYIYGEGGPFLSASINEALNNNGILSS\n"
                        + "FT                   VGK ->"
                        + " FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVFGVTHRQ\n"
                        + "FT                  "
                        + " SIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSLSIEHLLCSGPSDFADHYQLGELKA\n"
                        + "FT                   AIFSFIDEKTRTEQ (in isoform 2)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                        + "FT                   ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "FT                   /id=\"VSP_037399\"";
        String ftLineString =
                "VAR_SEQ 103..222\n"
                        + "/note=\"GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCALRPTYIYGEGGPFLSASINEALNNNGILSSVGK"
                        + " -> FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVFGVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSLSIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ"
                        + " (in isoform 2)\"\n"
                        + "/id=\"VSP_037399\"";

        String ftLineStringEv =
                "VAR_SEQ 103..222\n"
                        + "/note=\"GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCALRPTYIYGEGGPFLSASINEALNNNGILSSVGK"
                        + " -> FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVFGVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSLSIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ"
                        + " (in isoform 2)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1\"\n"
                        + "/id=\"VSP_037399\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";

        String originalSequence =
                "GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL"
                        + "RPTYIYGEGGPFLSASINEALNNNGILSSVGK";
        String alt =
                "FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF"
                        + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
                        + "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add(alt);
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("2");
        // report.add("Beta");
        FeatureLocation location = new FeatureLocation(103, 222);
        String featureId = "VSP_037399";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        String description = "in isoform 2";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
    
    @Test
    void testVarsplicRemovetrailSpace() {

        String ftLine =
                "FT   VAR_SEQ         1..166\n"
                + "FT                   /note=\"MRREFCWDAYSKAAGSRASSPLPRQDRDSFCHQMSFCLTELHLWSLKNTLHI\n"
                + "FT                   ADRDIGIYQYYDKKDPPATEHGNLEKKQKLAESRDYPWTLKNRRPEKLRDSLKELEELM\n"
                + "FT                   QNSRCVLSKWKNKYVCQLLFGSGVLVSLSLSGPQLEKVVIDRSLVGKLISDTISD ->\n"
                + "FT                   MFSSLHS (in isoform 3)\"\n"
                + "FT                   /evidence=\"ECO:0000303|PubMed:15489334, ECO:0000303|Ref.1\"\n"
                + "FT                   /id=\"VSP_032408\"";


        String originalSequence = "MRREFCWDAYSKAAGSRASSPLPRQDRDSFCHQMSFCLTELHLWSLKNTLHIADRDIGIYQYYDKKDPPATEHGNLEKKQKLAESRDYPWTLKNRRPEKLRDSLKELEELM"
        		+ "QNSRCVLSKWKNKYVCQLLFGSGVLVSLSLSGPQLEKVVIDRSLVGKLISDTISD";
        List<String> alternativeSequences = new ArrayList<>();
         alternativeSequences.add("MFSSLHS");
        FeatureLocation location = new FeatureLocation(1, 166);
        String featureId = "VSP_032408";
        String description = "in isoform 3";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000303|PubMed:15489334");
        evs.add("ECO:0000303|Ref.1");
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VAR_SEQ,
                        location,
                        description,
                        featureId,
                        altSeq,
                        evs);

        doTest(ftLine, feature);

    }

}
