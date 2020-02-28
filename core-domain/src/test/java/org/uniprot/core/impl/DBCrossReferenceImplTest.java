package org.uniprot.core.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.builder.DBCrossReferenceBuilder;

class DBCrossReferenceImplTest {

    private List<Property> properties =
            asList(new Property("key1", "value1"), new Property("key2", "value2"));
    private DBCrossReference<DefaultDatabase> xref =
            new DBCrossReferenceBuilder<DefaultDatabase>()
                    .databaseType(new DefaultDatabase("EMBL"))
                    .id("DB123414")
                    .propertiesSet(properties)
                    .build();

    @Test
    void testDBCrossReferenceImplStringString() {
        DBCrossReference<DefaultDatabase> xref =
                new DBCrossReferenceBuilder<DefaultDatabase>()
                        .databaseType(new DefaultDatabase("EMBL"))
                        .id("DB123414")
                        .build();
        verify(xref, "EMBL", "DB123414", Collections.emptyList());
    }

    @Test
    void testDBCrossReferenceImplStringStringListOfProperty() {
        verify(xref, "EMBL", "DB123414", properties);
    }

    private void verify(
            DBCrossReference<DefaultDatabase> xref,
            String dbName,
            String id,
            List<Property> properties) {
        assertEquals(dbName, xref.getDatabaseType().getName());
        assertEquals(id, xref.getId());
        assertEquals(properties, xref.getProperties());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        DBCrossReference<DefaultDatabase> obj = new DBCrossReferenceImpl<>();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<DefaultDatabase> impl =
                new DBCrossReferenceImpl<>(new DefaultDatabase("EMBL"), "one", properties);
        DBCrossReference<DefaultDatabase> obj = DBCrossReferenceBuilder.from(impl).build();

        assertTrue(impl.hasDatabaseType());
        assertTrue(impl.hasId());
        assertTrue(impl.hasProperties());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("EMBL:DB123414", xref.toString());
    }
}
