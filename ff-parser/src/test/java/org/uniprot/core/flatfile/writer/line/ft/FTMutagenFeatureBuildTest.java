package org.uniprot.core.flatfile.writer.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FTMutagenFeatureBuildTest extends FTBuildTestAbstr {
    @Test
    void testMutagen() {
        String ftLine =
                "FT   MUTAGEN         2\n"
                        + "FT                   /note=\"B->A,N: Less than 1% residual activity\"";
        String ftLineString = "MUTAGEN 2\n" + "/note=\"B->A,N: Less than 1% residual activity\"";
        String originalSequence = "B";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("A");
        alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Less than 1% residual activity");
        FeatureLocation location = new FeatureLocation(2, 2);
        List<String> evs = new ArrayList<>();
        String description = "Less than 1% residual activity";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.MUTAGEN, location, description, "", altSeq, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
    }

    @Test
    void testMutagenEvidence() {
        String ftLine =
                "FT   MUTAGEN         2\n"
                        + "FT                   /note=\"B->A,N: Less than 1% residual activity\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10433554,\n"
                        + "FT                   ECO:0000313|EMBL:BAG16761.1\"";
        String ftLineString = "MUTAGEN 2\n" + "/note=\"B->A,N: Less than 1% residual activity\"";
        String ftLineStringEv =
                "MUTAGEN 2\n"
                        + "/note=\"B->A,N: Less than 1% residual activity\"\n"
                        + "/evidence=\"ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1\"";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String originalSequence = "B";
        List<String> alternativeSequences = new ArrayList<>();
        alternativeSequences.add("A");
        alternativeSequences.add("N");
        List<String> report = new ArrayList<>();

        report.add("Less than 1% residual activity");
        FeatureLocation location = new FeatureLocation(2, 2);

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        String description = "Less than 1% residual activity";
        AlternativeSequence altSeq =
                createAlternativeSequence(originalSequence, alternativeSequences);
        UniProtKBFeature feature =
                createFeature(UniprotKBFeatureType.MUTAGEN, location, description, "", altSeq, evs);

        doTest(ftLine, feature);
        doTestString(ftLineString, feature);
        doTestStringEv(ftLineStringEv, feature);
    }
}
