package org.uniprot.core.flatfile.writer.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;

class FTCarbohydFeatureBuildTest extends FTBuildTestAbstr {
    @Test
    void testCARBOHYD() {
        String ftLine =
                "FT   CARBOHYD        61\n"
                        + "FT                   /note=\"N-linked (GlcNAc...); by host (Potential)\"";
        String ftLineString =
                "CARBOHYD 61\n" + "/note=\"N-linked (GlcNAc...); by host (Potential)\"";

        String description = "N-linked (GlcNAc...); by host (Potential)";
        String featureId = "";
        List<String> evs = new ArrayList<>();
        Feature feature = createFeature(FeatureType.CARBOHYD, 61, 61, description, featureId, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testCARBOHYDEvidence() {
        String ftLine =
                "FT   CARBOHYD        61\n"
                        + "FT                   /note=\"N-linked (GlcNAc...); by host (Potential)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,"
                        + " ECO:0000303|Ref.6\"";
        String ftLineString =
                "CARBOHYD 61\n" + "/note=\"N-linked (GlcNAc...); by host (Potential)\"";
        String ftLineStringEv =
                "CARBOHYD 61\n"
                        + "/note=\"N-linked (GlcNAc...); by host (Potential)\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6\"";
        //	String ev1 ="ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";

        String description = "N-linked (GlcNAc...); by host (Potential)";
        String featureId = "";

        List<String> evs = new ArrayList<>();
        evs.add(ev3);
        evs.add(ev2);
        Feature feature = createFeature(FeatureType.CARBOHYD, 61, 61, description, featureId, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
}
