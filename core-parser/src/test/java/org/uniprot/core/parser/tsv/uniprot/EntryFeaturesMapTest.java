package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.builder.AlternativeSequenceBuilder;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;

class EntryFeaturesMapTest {

    @Test
    void testGetData() {

        List<Feature> features = createTestFeatures();
        EntryFeaturesMap dl = new EntryFeaturesMap(features);
        Map<String, String> result = dl.attributeValues();
        assertEquals(4, result.size());
        verify("rs1064793108 rs1064793121", "dr:dbsnp", result);
        verify("DOMAIN 23 23 some domain.", "ft:domain", result);
        verify("HELIX 7 10 {ECO:0000244|PDB:2LO1}.", "ft:helix", result);
        String variantExp =
                "VARIANT 23 23 A -> G (in SCN1; dbSNP:rs1064793108). /FTId=VAR_064512.;"
                        + " VARIANT 27 27 B -> D (in another; dbSNP:rs1064793121)."
                        + " {ECO:0000269|PubMed:12345, ECO:0000269|PubMed:6142052}. /FTId=VAR_064556.";

        verify(variantExp, "ft:variant", result);
    }

    @Test
    void testGetFeatures() {
        List<Feature> features = createTestFeatures();
        List<String> featureList = EntryFeaturesMap.getFeatures(features);
        List<String> expected = Arrays.asList("Domain (1)", "Helix (1)", "Natural variant (2)");
        assertEquals(expected, featureList);
    }

    List<Feature> createTestFeatures() {
        List<Feature> features = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        features.add(
                createFeature(
                        FeatureType.VARIANT,
                        new Range(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        null,
                        createAlternativeSequence("A", "G")));
        Feature feature =
                createFeature(
                        FeatureType.VARIANT,
                        new Range(27, 27),
                        "in another; dbSNP:rs1064793121",
                        "VAR_064556",
                        evidences,
                        createAlternativeSequence("B", "D"));
        features.add(feature);
        features.add(
                createFeature(
                        FeatureType.DOMAIN, new Range(23, 23), "some domain", null, null, null));

        List<Evidence> evidences2 = new ArrayList<>();
        evidences2.add(createEvidence("ECO:0000244", "PDB", "2LO1"));
        Feature feature2 =
                createFeature(FeatureType.HELIX, new Range(7, 10), "", null, evidences2, null);
        features.add(feature2);

        return features;
    }

    private void verify(String expected, String field, Map<String, String> result) {
        String evaluated = result.get(field);
        assertEquals(expected, evaluated);
    }

    @Test
    void testFeatureWithFtIdToString() {
        Feature feature =
                createFeature(
                        FeatureType.VARIANT,
                        new Range(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        null,
                        createAlternativeSequence("A", "G"));
        String result = EntryFeaturesMap.featureToString(feature);
        String expected = "VARIANT 23 23 A -> G (in SCN1; dbSNP:rs1064793108). /FTId=VAR_064512.";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureWithFtIdEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));

        Feature feature =
                createFeature(
                        FeatureType.VARIANT,
                        new Range(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        evidences,
                        createAlternativeSequence("A", "G"));
        String result = EntryFeaturesMap.featureToString(feature);
        String expected =
                "VARIANT 23 23 A -> G (in SCN1; dbSNP:rs1064793108)."
                        + " {ECO:0000269|PubMed:12345, ECO:0000269|PubMed:6142052}. /FTId=VAR_064512.";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureToString() {
        Feature feature =
                createFeature(
                        FeatureType.DOMAIN, new Range(23, 23), "some domain", null, null, null);
        String result = EntryFeaturesMap.featureToString(feature);
        String expected = "DOMAIN 23 23 some domain.";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureWithEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        Feature feature =
                createFeature(
                        FeatureType.DOMAIN,
                        new Range(23, 23),
                        "some domain",
                        null,
                        evidences,
                        null);

        String result = EntryFeaturesMap.featureToString(feature);
        String expected =
                "DOMAIN 23 23 some domain. {ECO:0000269|PubMed:12345, ECO:0000269|PubMed:6142052}.";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureNoDescWithEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000244", "PDB", "2LO1"));
        Feature feature =
                createFeature(FeatureType.HELIX, new Range(7, 10), "", null, evidences, null);
        String result = EntryFeaturesMap.featureToString(feature);
        String expected = "HELIX 7 10 {ECO:0000244|PDB:2LO1}.";
        assertEquals(result, expected);
    }

    private AlternativeSequence createAlternativeSequence(String original, String alternative) {
        return new AlternativeSequenceBuilder().original(original).alternative(alternative).build();
    }

    private Feature createFeature(
            FeatureType type,
            Range range,
            String description,
            String ftid,
            List<Evidence> evidences,
            AlternativeSequence alternativeSequence) {
        return new FeatureBuilder()
                .type(type)
                .description(description)
                .featureId(ftid)
                .location(range)
                .evidences(evidences)
                .alternativeSequence(alternativeSequence)
                .build();
    }

    private Evidence createEvidence(String code, String dbType, String dbId) {
        return new EvidenceBuilder()
                .databaseId(dbId)
                .evidenceCode(EvidenceCode.typeOf(code))
                .databaseName(dbType)
                .build();
    }
}
