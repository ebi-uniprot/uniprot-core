package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.ft;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FeatureLineBuilderFactory;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidenceHelper;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.builder.AlternativeSequenceBuilder;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;

import static org.junit.Assert.assertEquals;

public class FTBuildTestAbstr {
    void doTest(String ftLine, Feature feature) {
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);

        FFLine ffLine = builder.buildWithEvidence(feature);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ftLine);
        assertEquals(ftLine, resultString);
    }

    void doTestString(String ftLine, Feature feature) {

        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);
        String value = builder.buildString(feature);
        System.out.println(value);
        assertEquals(ftLine, value);
    }

    void doTestStringEv(String ccLine, Feature feature) {

        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);
        String value = builder.buildStringWithEvidence(feature);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    Feature createFeature(FeatureType type, int nstart, int nend, String description, String ftId, List<String> evs) {
        return createFeature(type, nstart, nend, PositionModifier.EXACT, PositionModifier.EXACT, description, ftId,
                             evs);
    }

    Feature createFeature(FeatureType type, int nstart, int nend, PositionModifier sfModifier,
                          PositionModifier efModifier, String description, String ftId, List<String> evs) {
        Range location = new Range(new Position(nstart, sfModifier), new Position(nend, efModifier));

        return new FeatureBuilder()
                .type(type).location(location).description(description).featureId(ftId).evidences(createEvidence(evs))
                .build();
    }

    Feature createFeature(FeatureType type, Range location, String description, String ftId,
                          AlternativeSequence alternativeSequence, List<String> evs) {
        return new FeatureBuilder()
                .type(type).location(location).description(description).featureId(ftId)
                .alternativeSequence(alternativeSequence).evidences(createEvidence(evs))
                .build();
    }

    AlternativeSequence createAlternativeSequence(String originalSequence, List<String> alternativeSequences) {
        return new AlternativeSequenceBuilder().alternatives(alternativeSequences).original(originalSequence)
                .build();
    }

    protected List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }
}
