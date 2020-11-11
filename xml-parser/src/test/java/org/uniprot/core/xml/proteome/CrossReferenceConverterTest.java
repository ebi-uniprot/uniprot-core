package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.xml.jaxb.proteome.DbReferenceType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

class CrossReferenceConverterTest {
    private final ObjectFactory xmlFactory = new ObjectFactory();
    CrossReferenceConverter converter = new CrossReferenceConverter();

    @Test
    void testFromXml() {
        DbReferenceType xml = xmlFactory.createDbReferenceType();
        xml.setType("AssemblyId");
        xml.setId("someValue");
        CrossReference<ProteomeDatabase> xref = converter.fromXml(xml);
        assertEquals(ProteomeDatabase.ASSEMBLY_ID, xref.getDatabase());
        assertEquals("someValue", xref.getId());
        assertEquals(0, xref.getProperties().size());
    }

    @Test
    void testToXml() {
        CrossReferenceBuilder<ProteomeDatabase> builder = new CrossReferenceBuilder<>();

        CrossReference<ProteomeDatabase> xref =
                builder.database(ProteomeDatabase.GENOME_ASSEMBLY)
                        .id("AGA21341.1")
                        .propertiesAdd("propKey", "propValue")
                        .propertiesAdd("propKey2", "propValue2")
                        .build();
        DbReferenceType xml = converter.toXml(xref);

        CrossReference<ProteomeDatabase> converted = converter.fromXml(xml);
        assertEquals(xref, converted);
    }
}
