package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureIdBuilder;

import com.google.common.base.Strings;

class FeatureConverterTest {

    @Test
    void testVariant() {

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("T", Arrays.asList("I"));
        String description =
                "in CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785";
        String description2 =
                "In CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785.";
        String ftid = "VAR_015450";

        Feature feature = createFeature(FeatureType.VARIANT, 67, 67, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 67, 67, description2, ftid, "T", Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);

        // FT VARIANT 67 67 T -> I (in CSTNU; reduces catalytic
        // FT activity and affinity for pyridoxal
        // FT phosphate; dbSNP:rs28941785).
        // FT /FTId=VAR_015450.
    }

    @Test
    void testVariantNoEvidence() {

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("T", Arrays.asList("I"));
        String description =
                "in CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785";
        String ftid = "VAR_015450";
        String description2 =
                "In CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785.";
        Feature feature =
                createFeature(
                        FeatureType.VARIANT,
                        67,
                        67,
                        description,
                        ftid,
                        altSeq,
                        Collections.emptyList());

        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 67, 67, description2, ftid, "T", Collections.emptyList());
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);

        // FT VARIANT 67 67 T -> I (in CSTNU; reduces catalytic
        // FT activity and affinity for pyridoxal
        // FT phosphate; dbSNP:rs28941785).
        // FT /FTId=VAR_015450.
    }

    private AlternativeSequence createAlternativeSequence(
            String original, List<String> alternatives) {
        return new AlternativeSequenceBuilder()
                .original(original)
                .alternativeSequencesSet(alternatives)
                .build();
    }

    @Test
    void testVariantNoId() {

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("T", Arrays.asList("I"));
        String description =
                "in CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785";
        String description2 =
                "In CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate;"
                        + " dbSNP:rs28941785.";
        String ftid = null;

        Feature feature = createFeature(FeatureType.VARIANT, 67, 67, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 67, 67, description2, ftid, "T", Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);

        // FT VARIANT 67 67 T -> I (in CSTNU; reduces catalytic
        // FT activity and affinity for pyridoxal
        // FT phosphate; dbSNP:rs28941785).
        // FT /FTId=VAR_015450.
    }

    private void verify(
            org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj,
            int start,
            int end,
            String description,
            String ftid,
            String original,
            List<Integer> evidences) {
        assertEquals(evidences, xmlObj.getEvidence());
        assertEquals(description, xmlObj.getDescription());
        assertEquals(ftid, xmlObj.getId());
        if (start == end) {
            assertTrue(xmlObj.getLocation().getPosition() != null);
            assertEquals(start, xmlObj.getLocation().getPosition().getPosition().intValue());
        } else {
            assertEquals(start, xmlObj.getLocation().getBegin().getPosition().intValue());
            assertEquals(end, xmlObj.getLocation().getEnd().getPosition().intValue());
        }
        assertEquals(original, xmlObj.getOriginal());
    }

    @Test
    void testVarSeq() {
        // FT VAR_SEQ 153 196 Missing (in isoform 2).
        // FT {ECO:0000303|PubMed:1339280}.
        // FT /FTId=VSP_006306.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("", Collections.emptyList());
        String description = "in isoform 2";
        String description2 = "In isoform 2.";
        String ftid = "VSP_006306";

        Feature feature = createFeature(FeatureType.VAR_SEQ, 153, 196, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 153, 196, description2, ftid, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testHelix() {
        //	FT   HELIX        18     24       {ECO:0000244|PDB:3COG}.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = null;
        String description = "";
        String ftid = null;

        Feature feature = createFeature(FeatureType.HELIX, 18, 24, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 18, 24, null, ftid, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testDOMAIN() {
        //		FT   DOMAIN      109    322       Adrift-type SAM-dependent 2'-O-MTase.
        //	FT                                {ECO:0000255|PROSITE-ProRule:PRU00946}.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = null;
        String description = "Adrift-type SAM-dependent 2'-O-MTase";
        String ftid = null;

        Feature feature = createFeature(FeatureType.DOMAIN, 109, 322, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 109, 322, description, ftid, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testCHAIN() {
        //	FT   CHAIN         1    405       Cystathionine gamma-lyase.
        //	FT                                /FTId=PRO_0000114749.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = null;
        String description = "Cystathionine gamma-lyase";
        String ftid = "PRO_0000114749";

        Feature feature = createFeature(FeatureType.CHAIN, 1, 405, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 1, 405, description, ftid, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testConflict() {
        //	FT   CONFLICT    658    658       C -> S (in Ref. 2; AAI71654).
        //	FT                                {ECO:0000305}.

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("C", Arrays.asList("S"));
        String description = "in Ref. 2; AAI71654";
        String description2 = "In Ref. 2; AAI71654.";
        String ftid = "";

        Feature feature = createFeature(FeatureType.CONFLICT, 658, 658, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 658, 658, description2, null, "C", Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testConflict2() {
        //	FT   CONFLICT    658    658       C -> S (in Ref. 2; AAI71654).
        //	FT                                {ECO:0000305}.

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("C", Arrays.asList("S"));
        String description = "in Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724";
        String description2 = "In Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724.";
        String ftid = "";

        Feature feature = createFeature(FeatureType.CONFLICT, 658, 658, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 658, 658, description2, null, "C", Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testMissing() {
        //	FT   CONFLICT    658    658       Missing (in Ref. 2; AAI71654).
        //	FT                                {ECO:0000305}.

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("", Collections.emptyList());
        String description = "in Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724";
        String description2 = "In Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724.";
        String ftid = "";

        Feature feature = createFeature(FeatureType.CONFLICT, 658, 658, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 658, 658, description2, null, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testMUTAGEN() {
        // FT   MUTAGEN     188    188       G->R: In hot2-1; reduced tolerance to
        //	FT                                abiotic stresses such as salt, drought
        //	FT                                and heat. {ECO:0000269|PubMed:17156413}.

        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = createAlternativeSequence("G", Arrays.asList("R", "S"));
        String description =
                "In hot2-1; reduced tolerance to abiotic stresses such as salt, drought and heat";
        String description2 =
                "In hot2-1; reduced tolerance to abiotic stresses such as salt, drought and heat.";
        String ftid = "";

        Feature feature = createFeature(FeatureType.MUTAGEN, 188, 188, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 188, 188, description2, null, "G", Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testVarSeq2() {
        //	FT   VAR_SEQ    1158   1202       GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQP
        //	FT                                GLAT -> LLSHQEAGPGSPEDHEIPAALPTQDEGTEAEPG
        //	FT                                AGRASPAGTGHMTWPPPFSPPWGRLVQS (in isoform
        //	FT                                6). {ECO:0000303|PubMed:15489334}.
        //	FT                                /FTId=VSP_046059.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq =
                createAlternativeSequence(
                        "GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQPGLAT",
                        Arrays.asList("AGRASPAGTGHMTWPPPFSPPWGRLVQS"));
        String description = "in isoform 6";
        String description2 = "In isoform 6.";
        String ftid = "VSP_046059";

        Feature feature = createFeature(FeatureType.VAR_SEQ, 1158, 1202, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(
                xmlObj,
                1158,
                1202,
                description2,
                ftid,
                "GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQPGLAT",
                Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    @Test
    void testConflictDescriptionParser() {
        String description = "in Ref. 2; AAI71654";
        parseDescription(description);
        parseDescription("in Ref. 4; BAC42427 and 5; AAO64895");
        parseDescription("Missing (in Ref. 2; DAA06033, 3; CAM19021 and 4; AAI00306)");
        parseDescription(
                "Missing (in Ref. 1; AAZ04665 and 2;"
                        + " BAC33489/BAE29479/BAE29537/BAE41744/BAE34065)");
        parseDescription("In Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724");
        parseDescription("in Ref. 1");
        parseDescription("in Ref. 1 and 3");
    }

    private void parseDescription(String description) {
        String regex = ", | and ";
        String[] tokens = description.split(regex);
        System.out.println(Arrays.asList(tokens));
    }

    @Test
    void testConflictDescription() {
        String description = "in Ref. 2; AAI71654";
        List<Integer> result = FeatureConverter.extractConflictReference(description);
        List<Integer> expected = Arrays.asList(2);
        assertEquals(result, expected);
        description = "in Ref. 4; BAC42427 and 5; AAO64895";
        result = FeatureConverter.extractConflictReference(description);
        assertEquals(result, Arrays.asList(4, 5));

        description = "Missing (in Ref. 2; DAA06033, 3; CAM19021 and 4; AAI00306)";
        result = FeatureConverter.extractConflictReference(description);
        assertEquals(result, Arrays.asList(2, 3, 4));

        description =
                "Missing (in Ref. 1; AAZ04665 and 2; BAC33489/BAE29479/BAE29537/BAE41744/BAE34065)";
        result = FeatureConverter.extractConflictReference(description);
        assertEquals(result, Arrays.asList(1, 2));
        description = "In Ref. 1; BAB69494/BAB69495, 3; BAC32031 and 4; AAI16724";
        result = FeatureConverter.extractConflictReference(description);
        assertEquals(result, Arrays.asList(1, 3, 4));
    }

    @Test
    void testConflictDescriptionSingle() {
        String description = "in Ref. 1";
        List<Integer> result = FeatureConverter.extractConflictReference(description);
        List<Integer> expected = Arrays.asList(1);
        assertEquals(result, expected);
    }

    @Test
    void testConflictDescriptionTwo() {
        String description = "in Ref. 1 and 3";
        List<Integer> result = FeatureConverter.extractConflictReference(description);
        List<Integer> expected = Arrays.asList(1, 3);
        assertEquals(result, expected);
    }

    @Test
    void testCARBOHYD() {
        //	FT   CARBOHYD     96     96       N-linked (GlcNAc...) asparagine; by host.
        //	FT                                {ECO:0000255}.
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        FeatureConverter converter = new FeatureConverter(evRefMapper);
        AlternativeSequence altSeq = null;
        String description = "N-linked (GlcNAc...) asparagine; by host";
        String ftid = null;

        Feature feature = createFeature(FeatureType.CARBOHYD, 96, 96, description, ftid, altSeq);
        org.uniprot.core.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlObj, org.uniprot.core.xml.jaxb.uniprot.FeatureType.class, "feature"));
        verify(xmlObj, 96, 96, description, ftid, null, Arrays.asList(1, 2));
        Feature converted = converter.fromXml(xmlObj);
        assertEquals(feature, converted);
    }

    private Feature createFeature(
            FeatureType type,
            int start,
            int end,
            String description,
            String ftid,
            AlternativeSequence altSeq) {
        return createFeature(type, start, end, description, ftid, altSeq, createEvidences());
    }

    private Feature createFeature(
            FeatureType type,
            int start,
            int end,
            String description,
            String ftid,
            AlternativeSequence altSeq,
            List<Evidence> evidences) {
        FeatureId featureId = null;
        if (!Strings.isNullOrEmpty(ftid)) {
            featureId = new FeatureIdBuilder(ftid).build();
        }
        return new FeatureBuilder()
                .type(type)
                .location(new FeatureLocation(start, end))
                .description(description)
                .featureId(featureId)
                .alternativeSequence(altSeq)
                .evidencesSet(evidences)
                .build();
    }
}
