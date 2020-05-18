package org.uniprot.core.xml.feature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.xml.feature.antigen.FeatureConverter;
import org.uniprot.core.xml.jaxb.feature.EvidenceType;
import org.uniprot.core.xml.jaxb.feature.FeatureType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
public class FeatureEvidenceConverterTest {

    @Test
    void testConvertSimple() {
        FeatureEvidenceConverter converter = new FeatureEvidenceConverter();
        Evidence evidence = new EvidenceBuilder().build();
        EvidenceType xml = converter.toXml(evidence);
        Evidence converted = converter.fromXml(xml);
        assertEquals(evidence, converted);
    }

    @Test
    void testConvertComplete() {
        FeatureEvidenceConverter converter = new FeatureEvidenceConverter();
        Evidence evidence = createEvidence();
        EvidenceType xml = converter.toXml(evidence);
        Evidence converted = converter.fromXml(xml);
        assertEquals(evidence, converted);
    }

    public static Evidence createEvidence(){
        return new EvidenceBuilder()
                .databaseName("HPA")
                .databaseId("HPA12345")
                .evidenceCode(EvidenceCode.ECO_0000255)
                .build();
    }
}