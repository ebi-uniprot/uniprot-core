package org.uniprot.core.flatfile.writer.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FTSimpleFeatureBuildTest extends FTBuildTestAbstr {
    @Test
    void test1() {
        String ftLine =
                "FT   ACT_SITE        1691..1871\n"
                        + "FT                   /note=\"VWFA 3; main binding site for collagens type I and\n"
                        + "FT                   III\"";
        String ftLineString =
                "ACT_SITE 1691..1871\n"
                        + "/note=\"VWFA 3; main binding site for collagens type I and III\"";
        String description = "VWFA 3; main binding site for collagens type I and III";
        List<String> evs = new ArrayList<>();

        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.ACT_SITE, 1691, 1871, description, null, evs);

        doTest(ftLine, feature);

        doTestString(ftLineString, feature);
    }

    @Test
    void test2() {
        String ftLine =
                "FT   CHAIN           61..386\n"
                        + "FT                   /note=\"Serine/threonine-protein phosphatase 2A 56 kDa\n"
                        + "FT                   regulatory subunit gamma isoform\"\n"
                        + "FT                   /id=\"PRO_0000071458\"";
        String ftLineString =
                "CHAIN 61..386\n"
                        + "/note=\"Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform\"\n"
                        + "/id=\"PRO_0000071458\"";
        String description4 =
                "Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform";
        List<String> evs4 = new ArrayList<>();

        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.CHAIN, 61, 386, description4, "PRO_0000071458", evs4);
        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void test3() {
        String ftLine =
                "FT   BINDING         79..197\n"
                        + "FT                   /note=\"Response regulatory (By similarity)\"";
        String ftLineString = "BINDING 79..197\n" + "/note=\"Response regulatory (By similarity)\"";
        String description2 = "Response regulatory (By similarity)";
        List<String> evs2 = new ArrayList<>();
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 79, 197, description2, null, evs2);
        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void test1Ev() {
        String ftLine =
                "FT   ACT_SITE        1691..1871\n"
                        + "FT                   /note=\"VWFA 3; main binding site for collagens type I and\n"
                        + "FT                   III\"\n"
                        + "FT                   /evidence=\"ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1\"";
        String ftLineString =
                "ACT_SITE 1691..1871\n"
                        + "/note=\"VWFA 3; main binding site for collagens type I and III\"";
        String ftLineStringEv =
                "ACT_SITE 1691..1871\n"
                        + "/note=\"VWFA 3; main binding site for collagens type I and III\"\n"
                        + "/evidence=\"ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev3 = "ECO:0000303|Ref.6";

        String description = "VWFA 3; main binding site for collagens type I and III";
        List<String> evs = new ArrayList<>();
        evs.add(ev3);
        evs.add(ev1);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.ACT_SITE, 1691, 1871, description, null, evs);

        doTest(ftLine, feature);

        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    void test2Ev() {
        String ftLine =
                "FT   CHAIN           61..386\n"
                        + "FT                   /note=\"Serine/threonine-protein phosphatase 2A 56 kDa\n"
                        + "FT                   regulatory subunit gamma isoform\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_00205\"\n"
                        + "FT                   /id=\"PRO_0000071458\"";
        String ftLineString =
                "CHAIN 61..386\n"
                        + "/note=\"Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform\"\n"
                        + "/id=\"PRO_0000071458\"";
        String ftLineStringEv =
                "CHAIN 61..386\n"
                        + "/note=\"Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform\"\n"
                        + "/evidence=\"ECO:0000256|HAMAP-Rule:MF_00205\"\n"
                        + "/id=\"PRO_0000071458\"";
        String description4 =
                "Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform";
        List<String> evs4 = new ArrayList<>();
        evs4.add("ECO:0000256|HAMAP-Rule:MF_00205");
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.CHAIN, 61, 386, description4, "PRO_0000071458", evs4);
        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }

    @Test
    void test3Ev() {
        String ftLine =
                "FT   BINDING         79..197\n"
                        + "FT                   /note=\"Response regulatory (By similarity)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                        + "FT                   ECO:0000313|PDB:3OW2\"";
        String ftLineString = "BINDING 79..197\n" + "/note=\"Response regulatory (By similarity)\"";
        String ftLineStringEv =
                "BINDING 79..197\n"
                        + "/note=\"Response regulatory (By similarity)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|PDB:3OW2\"";
        String description2 = "Response regulatory (By similarity)";
        List<String> evs2 = new ArrayList<>();
        evs2.add("ECO:0000269|PubMed:10433554");
        evs2.add("ECO:0000313|PDB:3OW2");
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.BINDING, 79, 197, description2, null, evs2);
        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
}
