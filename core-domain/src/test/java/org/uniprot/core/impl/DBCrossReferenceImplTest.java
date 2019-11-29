package org.uniprot.core.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.builder.DBCrossReferenceBuilder;

class DBCrossReferenceImplTest {

    private List<Property> properties = asList(new Property("key1", "value1"), new Property("key2", "value2"));
    private DBCrossReference<DefaultDatabaseType> xref = new DBCrossReferenceBuilder<DefaultDatabaseType>()
          .databaseType(new DefaultDatabaseType("EMBL"))
          .id("DB123414")
          .properties(properties)
          .build();

    @Test
    void testDBCrossReferenceImplStringString() {
        DBCrossReference<DefaultDatabaseType> xref =
                new DBCrossReferenceBuilder<DefaultDatabaseType>()
                        .databaseType(new DefaultDatabaseType("EMBL"))
                        .id("DB123414")
                        .build();
        verify(xref, "EMBL", "DB123414", Collections.emptyList());
    }

    @Test
    void testDBCrossReferenceImplStringStringListOfProperty() {
        verify(xref, "EMBL", "DB123414", properties);
    }

    private void verify(
            DBCrossReference<DefaultDatabaseType> xref,
            String dbName,
            String id,
            List<Property> properties) {
        assertEquals(dbName, xref.getDatabaseType().getName());
        assertEquals(id, xref.getId());
        assertEquals(properties, xref.getProperties());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        DBCrossReference<DefaultDatabaseType> obj = new DBCrossReferenceImpl<>();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<DefaultDatabaseType> impl = new DBCrossReferenceImpl<>(new DefaultDatabaseType("EMBL"),
          "one", properties);
        DBCrossReference<DefaultDatabaseType> obj = new DBCrossReferenceBuilder<DefaultDatabaseType>().from(impl).build();

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
