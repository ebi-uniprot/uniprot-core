package org.uniprot.core.scorer.uniprotkb.feature;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.UniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.scorer.uniprotkb.features.FeatureScored;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.feature.Feature;

class FeatureWithEvidenceScoredTest {
    @Test
    void shouldModResScore3() {
        String ftLine =
                "FT   MOD_RES         117\n"
                        + "FT                   /note=\"2-(S-cysteinyl)pyruvic acid O-phosphothioketal (By similarity)\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_00111\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 3.0, singletonList(new EvidenceDatabase("HAMAP-Rule")));
    }

    @Test
    void shouldModResScore0() {
        String ftLine =
                "FT   MOD_RES         117\n"
                        + "FT                   /note=\"2-(S-cysteinyl)pyruvic acid O-phosphothioketal (By similarity)\"\n"
                        + "FT                   /evidence=\"ECO:0000256|HAMAP-Rule:MF_00111\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceDatabase("RuleBase")));
    }

    @Test
    void shouldTransMemScore3() {
        String ftLine =
                "FT   TRANSMEM        789..809\n"
                        + "FT                   /note=\"Helical\"\n"
                        + "FT                   /evidence=\"ECO:0000256|SAM:Phobius\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 3.0, singletonList(new EvidenceDatabase("SAM")));
    }

    @Test
    void shouldTransMemScore0() {
        String ftLine =
                "FT   TRANSMEM        789..809\n"
                        + "FT                   /note=\"Helical\"\n"
                        + "FT                   /evidence=\"ECO:0000256|SAM:Phobius\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceDatabase("HAMAP-Rule")));
    }

    @Test
    void shouldDOMAINScore00() {
        String ftLine =
                "FT   DOMAIN          1..438\n"
                        + "FT                   /note=\"SPX\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS51382\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceDatabase("PROSITE")));
    }

    @Test
    void shouldDOMAINScore0() {
        String ftLine =
                "FT   DOMAIN          1..438\n"
                        + "FT                   /note=\"SPX\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS51382\"\n";

        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceDatabase("SAM")));
    }

    private void verify(Feature feature, double expectedScore, List<EvidenceDatabase> types) {
        FeatureScored scored = new FeatureScored(feature, types);
        assertEquals(expectedScore, scored.score(), 0.00001);
    }

    private Feature createFeature(String ftLine) {
        UniprotkbLineParserFactory parserFactory = new DefaultUniprotkbLineParserFactory();
        UniprotkbLineParser<FtLineObject> parser = parserFactory.createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        FtLineConverter converter = new FtLineConverter();
        List<Feature> features = converter.convert(obj);
        return features.get(0);
    }
}
