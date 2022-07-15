package org.uniprot.core.xml.feature.antigen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.xml.feature.FeatureEvidenceConverterTest;
import org.uniprot.core.xml.feature.FeatureLocationConverterTest;
import org.uniprot.core.xml.jaxb.feature.FeatureType;

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
                .featureCrossReferenceAdd(CrossReferenceConverterTest.createCrossReference())
                .location(FeatureLocationConverterTest.createFeatureLocation())
                .evidencesAdd(FeatureEvidenceConverterTest.createEvidence())
                .matchScore(98)
                .antigenSequence("AAAAA")
                .build();
    }
}
