package org.uniprot.core.xml.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.xml.jaxb.feature.SequenceType;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
class FeatureSequenceConverterTest {

    @Test
    void testConvert() {
        FeatureSequenceConverter converter = new FeatureSequenceConverter();
        Sequence sequence = new SequenceBuilder("AAA").build();
        SequenceType xml = converter.toXml(sequence);
        Sequence converted = converter.fromXml(xml);
        assertEquals(sequence, converted);
    }
}
