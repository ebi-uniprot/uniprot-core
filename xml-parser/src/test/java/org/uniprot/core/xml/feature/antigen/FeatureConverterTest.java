package org.uniprot.core.xml.feature.antigen;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.xml.feature.FeatureEvidenceConverterTest;
import org.uniprot.core.xml.feature.FeatureLocationConverterTest;
import org.uniprot.core.xml.jaxb.feature.EntryFeature;
import org.uniprot.core.xml.jaxb.feature.FeatureType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
class FeatureConverterTest {

    @Test
    void testConvertSimple() {
        FeatureConverter converter = new FeatureConverter();
        AntigenFeature antigenFeature = new AntigenFeatureBuilder().build();
        FeatureType xml = converter.toXml(antigenFeature);
        AntigenFeature converted = converter.fromXml(xml);
        assertEquals(antigenFeature, converted);
    }

    @Test
    void testConvertComplete() {
        FeatureConverter converter = new FeatureConverter();
        AntigenFeature antigenFeature = getAntigenFeature();
        FeatureType xml = converter.toXml(antigenFeature);
        AntigenFeature converted = converter.fromXml(xml);
        assertEquals(antigenFeature, converted);
    }

    static AntigenFeature getAntigenFeature() {
        return new AntigenFeatureBuilder()
                .type(AntigenFeatureType.ANTIGEN)
                .featureCrossReference(CrossReferenceConverterTest.createCrossReference())
                .location(FeatureLocationConverterTest.createFeatureLocation())
                .evidencesAdd(FeatureEvidenceConverterTest.createEvidence())
                .matchScore(98)
                .antigenSequence("AAAAA")
                .build();
    }
}