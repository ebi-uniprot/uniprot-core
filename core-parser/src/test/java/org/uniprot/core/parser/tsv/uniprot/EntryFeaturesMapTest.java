package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;

class EntryFeaturesMapTest {

    @Test
    void testGetData() {

        List<UniProtKBFeature> features = createTestFeatures();
        EntryFeaturesMap dl = new EntryFeaturesMap(features);
        Map<String, String> result = dl.attributeValues();
        assertEquals(4, result.size());
        verify("rs1064793108 rs1064793121", "xref_dbsnp", result);
        verify("DOMAIN 23 /note=\"some domain\"", "ft_domain", result);
        verify("HELIX 7..10 /evidence=\"ECO:0000244|PDB:2LO1\"", "ft_helix", result);
        String variantExp =
                "VARIANT 23 /note=\"A -> G (in SCN1; dbSNP:rs1064793108)\" /id=\"VAR_064512\";"
                        + " VARIANT 27 /note=\"B -> D (in another; dbSNP:rs1064793121)\""
                        + " /evidence=\"ECO:0000269|PubMed:12345, ECO:0000269|PubMed:6142052\""
                        + " /id=\"VAR_064556\"";

        verify(variantExp, "ft_variant", result);
    }

    @Test
    void testGetFeatures() {
        List<UniProtKBFeature> features = createTestFeatures();
        List<String> featureList = EntryFeaturesMap.getFeatures(features);
        List<String> expected = Arrays.asList("Domain (1)", "Helix (1)", "Natural variant (2)");
        assertEquals(expected, featureList);
    }

    List<UniProtKBFeature> createTestFeatures() {
        List<UniProtKBFeature> features = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        features.add(
                createFeature(
                        UniprotKBFeatureType.VARIANT,
                        new FeatureLocation(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        null,
                        createAlternativeSequence("A", "G")));
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VARIANT,
                        new FeatureLocation(27, 27),
                        "in another; dbSNP:rs1064793121",
                        "VAR_064556",
                        evidences,
                        createAlternativeSequence("B", "D"));
        features.add(feature);
        features.add(
                createFeature(
                        UniprotKBFeatureType.DOMAIN,
                        new FeatureLocation(23, 23),
                        "some domain",
                        null,
                        null,
                        null));

        List<Evidence> evidences2 = new ArrayList<>();
        evidences2.add(createEvidence("ECO:0000244", "PDB", "2LO1"));
        UniProtKBFeature feature2 =
                createFeature(
                        UniprotKBFeatureType.HELIX,
                        new FeatureLocation(7, 10),
                        "",
                        null,
                        evidences2,
                        null);
        features.add(feature2);

        return features;
    }

    private void verify(String expected, String field, Map<String, String> result) {
        String evaluated = result.get(field);
        assertEquals(expected, evaluated);
    }

    @Test
    void testFeatureWithFtIdToString() {
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VARIANT,
                        new FeatureLocation(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        null,
                        createAlternativeSequence("A", "G"));
        String result = EntryFeaturesMap.featureToString(feature);
        String expected =
                "VARIANT 23 /note=\"A -> G (in SCN1; dbSNP:rs1064793108)\" /id=\"VAR_064512\"";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureWithFtIdEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));

        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.VARIANT,
                        new FeatureLocation(23, 23),
                        "in SCN1; dbSNP:rs1064793108",
                        "VAR_064512",
                        evidences,
                        createAlternativeSequence("A", "G"));
        String result = EntryFeaturesMap.featureToString(feature);
        String expected =
                "VARIANT 23 /note=\"A -> G (in SCN1; dbSNP:rs1064793108)\""
                        + " /evidence=\"ECO:0000269|PubMed:12345, ECO:0000269|PubMed:6142052\""
                        + " /id=\"VAR_064512\"";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureToString() {
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.DOMAIN,
                        new FeatureLocation(23, 23),
                        "some domain",
                        null,
                        null,
                        null);
        String result = EntryFeaturesMap.featureToString(feature);
        String expected = "DOMAIN 23 /note=\"some domain\"";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureWithEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000269", "PubMed", "6142052"));
        evidences.add(createEvidence("ECO:0000269", "PubMed", "12345"));
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.DOMAIN,
                        new FeatureLocation(23, 23),
                        "some domain",
                        null,
                        evidences,
                        null);

        String result = EntryFeaturesMap.featureToString(feature);
        String expected =
                "DOMAIN 23 /note=\"some domain\" /evidence=\"ECO:0000269|PubMed:12345,"
                        + " ECO:0000269|PubMed:6142052\"";
        assertEquals(result, expected);
    }

    @Test
    void testFeatureNoDescWithEvidenceToString() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000244", "PDB", "2LO1"));
        UniProtKBFeature feature =
                createFeature(
                        UniprotKBFeatureType.HELIX,
                        new FeatureLocation(7, 10),
                        "",
                        null,
                        evidences,
                        null);
        String result = EntryFeaturesMap.featureToString(feature);
        String expected = "HELIX 7..10 /evidence=\"ECO:0000244|PDB:2LO1\"";
        assertEquals(result, expected);
    }

    private AlternativeSequence createAlternativeSequence(String original, String alternative) {
        return new AlternativeSequenceBuilder()
                .original(original)
                .alternativeSequencesAdd(alternative)
                .build();
    }

    private UniProtKBFeature createFeature(
            UniprotKBFeatureType type,
            FeatureLocation range,
            String description,
            String ftid,
            List<Evidence> evidences,
            AlternativeSequence alternativeSequence) {
        return new UniProtKBFeatureBuilder()
                .type(type)
                .description(description)
                .featureId(ftid)
                .location(range)
                .evidencesSet(evidences)
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
