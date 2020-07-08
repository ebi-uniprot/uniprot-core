package org.uniprot.core.xml.feature.antigen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.feature.DbReferenceType;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
class CrossReferenceConverterTest {

    @Test
    void testConvertSimple() {
        CrossReferenceConverter converter = new CrossReferenceConverter();
        CrossReference<AntigenDatabase> crossRef =
                new CrossReferenceBuilder<AntigenDatabase>().build();
        DbReferenceType xml = converter.toXml(crossRef);
        CrossReference<AntigenDatabase> converted = converter.fromXml(xml);
        assertEquals(crossRef, converted);
    }

    @Test
    void testConvertComplete() {
        CrossReferenceConverter converter = new CrossReferenceConverter();
        CrossReference<AntigenDatabase> crossRef = createCrossReference();
        DbReferenceType xml = converter.toXml(crossRef);
        CrossReference<AntigenDatabase> converted = converter.fromXml(xml);
        assertEquals(crossRef, converted);
    }

    static CrossReference<AntigenDatabase> createCrossReference() {
        return new CrossReferenceBuilder<AntigenDatabase>()
                .database(AntigenDatabase.ENSEMBL)
                .id("ENST00000346997")
                .propertiesAdd("GeneId", "ENSG00000263451")
                .propertiesAdd("ProteinId", "ENSP00000066468")
                .build();
    }
}
