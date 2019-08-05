package uk.ebi.uniprot.scorer.uniprotkb.feature;

import org.junit.Test;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.feature.Feature;

import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineObject;
import uk.ebi.uniprot.scorer.uniprotkb.features.FeatureScored;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class FeatureWithEvidenceScoredTest {
    @Test
    public void shouldModResScore3() {
        String ftLine =
                "FT   MOD_RES     117    117       2-(S-cysteinyl)pyruvic acid O-\n" +
                        "FT                                phosphothioketal (By similarity).\n" +
                        "FT                                {ECO:0000256|HAMAP-Rule:MF_00111}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 3.0, singletonList(new EvidenceType("HAMAP-Rule")));
    }

    @Test
    public void shouldModResScore0() {
        String ftLine =
                "FT   MOD_RES     117    117       2-(S-cysteinyl)pyruvic acid O-\n" +
                        "FT                                phosphothioketal (By similarity).\n" +
                        "FT                                {ECO:0000256|HAMAP-Rule:MF_00111}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceType("RuleBase")));
    }

    @Test
    public void shouldTransMemScore3() {
        String ftLine =
                "FT   TRANSMEM    789    809       Helical. {ECO:0000256|SAM:Phobius}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 3.0, singletonList(new EvidenceType("SAM")));
    }

    @Test
    public void shouldTransMemScore0() {
        String ftLine =
                "FT   TRANSMEM    789    809       Helical. {ECO:0000256|SAM:Phobius}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceType("HAMAP-Rule")));
    }

    @Test
    public void shouldDOMAINScore00() {
        String ftLine =
                "FT   DOMAIN        1    438       SPX. {ECO:0000259|PROSITE:PS51382}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceType("PROSITE")));
    }

    @Test
    public void shouldDOMAINScore0() {
        String ftLine =
                "FT   DOMAIN        1    438       SPX. {ECO:0000259|PROSITE:PS51382}.\n";
        Feature feature = createFeature(ftLine);
        verify(feature, 0.0, singletonList(new EvidenceType("SAM")));
    }

    private void verify(Feature feature, double expectedScore, List<EvidenceType> types) {
        FeatureScored scored = new FeatureScored(feature, types);
        assertEquals(expectedScore, scored.score(), 0.00001);
    }

    private Feature createFeature(String ftLine) {
        UniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();
        UniprotLineParser<FtLineObject> parser = parserFactory.createFtLineParser();
        FtLineObject obj = parser.parse(ftLine);
        FtLineConverter converter = new FtLineConverter();
        List<Feature> features = converter.convert(obj);
        return features.get(0);
    }
}
