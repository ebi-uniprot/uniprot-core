package org.uniprot.core.xml.feature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.xml.jaxb.feature.DbReferenceType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
class EvidenceCrossRefConverterTest {

    @Test
    void testConvertSimple() {
        EvidenceCrossRefConverter converter = new EvidenceCrossRefConverter();
        CrossReference<EvidenceDatabase> crossReference = new CrossReferenceBuilder<EvidenceDatabase>().build();
        DbReferenceType xml = converter.toXml(crossReference);
        CrossReference<EvidenceDatabase> converted = converter.fromXml(xml);
        assertEquals(crossReference, converted);
    }

    @Test
    void testConvertComplete() {
        EvidenceCrossRefConverter converter = new EvidenceCrossRefConverter();
        CrossReference<EvidenceDatabase> crossReference = createCrossReference();
        DbReferenceType xml = converter.toXml(crossReference);
        CrossReference<EvidenceDatabase> converted = converter.fromXml(xml);
        assertEquals(crossReference, converted);
    }

    private CrossReference<EvidenceDatabase> createCrossReference() {
        return new CrossReferenceBuilder<EvidenceDatabase>()
                .propertiesAdd("key", "value")
                .id("id value")
                .database(new EvidenceDatabase("HPA"))
                .build();
    }


}