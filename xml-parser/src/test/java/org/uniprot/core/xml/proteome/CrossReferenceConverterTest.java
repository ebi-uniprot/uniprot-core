package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.proteome.ProteomeDatabase;
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
        DBCrossReference<ProteomeDatabase> xref = converter.fromXml(xml);
        assertEquals(ProteomeDatabase.ASSEMBLY_ID, xref.getDatabaseType());
        assertEquals("someValue", xref.getId());
        assertEquals(0, xref.getProperties().size());
    }

    @Test
    void testToXml() {
        DBCrossReferenceBuilder<ProteomeDatabase> builder =
                new DBCrossReferenceBuilder<ProteomeDatabase>();

        DBCrossReference<ProteomeDatabase> xref =
                builder.databaseType(ProteomeDatabase.GENOME_ASSEMBLY).id("AGA21341.1").build();
        DbReferenceType xml = converter.toXml(xref);

        DBCrossReference<ProteomeDatabase> converted = converter.fromXml(xml);
        assertEquals(xref, converted);
    }
}
