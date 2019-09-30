package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.proteome.ProteomeXReferenceType;
import org.uniprot.core.xml.jaxb.proteome.DbReferenceType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

class CrossReferenceConverterTest {
    private ObjectFactory xmlFactory = new ObjectFactory();
    CrossReferenceConverter converter = new CrossReferenceConverter();

    @Test
    void testFromXml() {
        DbReferenceType xml = xmlFactory.createDbReferenceType();
        xml.setType("AssemblyId");
        xml.setId("someValue");
        DBCrossReference<ProteomeXReferenceType> xref = converter.fromXml(xml);
        assertEquals(ProteomeXReferenceType.ASSEMBLY_ID, xref.getDatabaseType());
        assertEquals("someValue", xref.getId());
        assertEquals(0, xref.getProperties().size());
    }

    @Test
    void testToXml() {
        DBCrossReferenceBuilder<ProteomeXReferenceType> builder =
                new DBCrossReferenceBuilder<ProteomeXReferenceType>();

        DBCrossReference<ProteomeXReferenceType> xref =
                builder.databaseType(ProteomeXReferenceType.GENOME_ASSEMBLY)
                        .id("AGA21341.1")
                        .build();
        DbReferenceType xml = converter.toXml(xref);

        DBCrossReference<ProteomeXReferenceType> converted = converter.fromXml(xml);
        assertEquals(xref, converted);
    }
}
