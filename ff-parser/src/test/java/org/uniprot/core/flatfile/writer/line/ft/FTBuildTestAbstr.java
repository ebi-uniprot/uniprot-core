package org.uniprot.core.flatfile.writer.line.ft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.PositionModifier;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.flatfile.parser.impl.ft.FeatureLineBuilderFactory;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;
import org.uniprot.cv.evidence.EvidenceHelper;

class FTBuildTestAbstr {
    void doTest(String ftLine, UniProtKBFeature feature) {
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        FFLine ffLine = builder.buildWithEvidence(feature);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ftLine);
        assertEquals(ftLine, resultString);
    }

    void doTestString(String ftLine, UniProtKBFeature feature) {

        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);
        String value = builder.buildString(feature);
        System.out.println(value);
        assertEquals(ftLine, value);
    }

    void doTestStringEv(String ccLine, UniProtKBFeature feature) {

        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);
        String value = builder.buildStringWithEvidence(feature);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    UniProtKBFeature createFeature(
            UniprotKBFeatureType type,
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

    UniProtKBFeature createFeature(
            UniprotKBFeatureType type,
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

        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .featureId(ftId)
                .evidencesSet(createEvidence(evs))
                .build();
    }

    UniProtKBFeature createFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            String description,
            String ftId,
            AlternativeSequence alternativeSequence,
            List<String> evs) {
        return new UniProtKBFeatureBuilder()
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
