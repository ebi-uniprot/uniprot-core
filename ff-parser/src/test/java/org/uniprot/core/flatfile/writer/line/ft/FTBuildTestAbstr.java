package org.uniprot.core.flatfile.writer.line.ft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.parser.impl.ft.FeatureLineBuilderFactory;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;
import org.uniprot.cv.evidence.EvidenceHelper;

class FTBuildTestAbstr {
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

    Feature createFeature(
            FeatureType type,
            int nstart,
            int nend,
            String description,
            String ftId,
            List<String> evs) {
        return createFeature(
                type,
                null,
                nstart,
                nend,
                PositionModifier.EXACT,
                PositionModifier.EXACT,
                description,
                ftId,
                evs);
    }

    Feature createFeature(
            FeatureType type,
            String sequence,
            int nstart,
            int nend,
            PositionModifier sfModifier,
            PositionModifier efModifier,
            String description,
            String ftId,
            List<String> evs) {
        FeatureLocation location =
                new FeatureLocation(sequence, nstart, nend, sfModifier, efModifier);

        return new FeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .featureId(ftId)
                .evidencesSet(createEvidence(evs))
                .build();
    }

    Feature createFeature(
            FeatureType type,
            FeatureLocation location,
            String description,
            String ftId,
            AlternativeSequence alternativeSequence,
            List<String> evs) {
        return new FeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .featureId(ftId)
                .alternativeSequence(alternativeSequence)
                .evidencesSet(createEvidence(evs))
                .build();
    }

    AlternativeSequence createAlternativeSequence(
            String originalSequence, List<String> alternativeSequences) {
        return new AlternativeSequenceBuilder()
                .alternativeSequencesSet(alternativeSequences)
                .original(originalSequence)
                .build();
    }

    protected List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }
}
