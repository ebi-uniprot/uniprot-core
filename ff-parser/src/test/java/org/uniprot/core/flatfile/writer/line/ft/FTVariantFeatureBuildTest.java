package org.uniprot.core.flatfile.writer.line.ft;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;

class FTVariantFeatureBuildTest extends FTBuildTestAbstr {
    @Test
    void testVariant() {
        String ftLine =
                "FT   VARIANT         221\n"
                    + "FT                   /note=\"G -> E (in a breast cancer sample; somatic"
                    + " mutation;\n"
                    + "FT                   dbSNP:rs35514614)\"\n"
                    + "FT                   /id=\"VAR_038685\"";
        String ftLineString =
                "VARIANT 221\n"
                    + "/note=\"G -> E (in a breast cancer sample; somatic mutation;"
                    + " dbSNP:rs35514614)\"\n"
                    + "/id=\"VAR_038685\"";

        String originalSequence = "G";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("E");
        // alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("in a breast cancer sample; somatic mutation; dbSNP:rs35514614");
        FeatureLocation location = new FeatureLocation(221, 221);
        String featureId = "VAR_038685";

        List<String> evs = new ArrayList<>();
        String description = "in a breast cancer sample; somatic mutation; dbSNP:rs35514614";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        Feature feature =
                createFeature(FeatureType.VARIANT, location, description, featureId, altSeq, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testVariantEvidence() {
        String ftLine =
                "FT   VARIANT         221\n"
                    + "FT                   /note=\"G -> E,D (in a breast cancer sample; somatic\n"
                    + "FT                   mutation; dbSNP:rs35514614)\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                    + "FT                   ECO:0000313|EMBL:BAG16761.1\"\n"
                    + "FT                   /id=\"VAR_038685\"";
        String ftLineString =
                "VARIANT 221\n"
                    + "/note=\"G -> E,D (in a breast cancer sample; somatic mutation;"
                    + " dbSNP:rs35514614)\"\n"
                    + "/id=\"VAR_038685\"";
        String ftLineStringEv =
                "VARIANT 221\n"
                    + "/note=\"G -> E,D (in a breast cancer sample; somatic mutation;"
                    + " dbSNP:rs35514614)\"\n"
                    + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1\"\n"
                    + "/id=\"VAR_038685\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";

        String originalSequence = "G";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("E");
        alternativeSequences.add("D");
        List<String> report = new ArrayList<>();

        report.add("in a breast cancer sample; somatic mutation; dbSNP:rs35514614");
        FeatureLocation location = new FeatureLocation(221, 221);
        String featureId = "VAR_038685";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        String description = "in a breast cancer sample; somatic mutation; dbSNP:rs35514614";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        Feature feature =
                createFeature(FeatureType.VARIANT, location, description, featureId, altSeq, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
}
