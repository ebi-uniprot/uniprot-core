package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.builder.DBCrossReferenceBuilder;

class DBCrossReferenceImplTest {
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
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("key1", "value1"));
        properties.add(new Property("key2", "value2"));

        DBCrossReference<DefaultDatabaseType> xref =
                new DBCrossReferenceBuilder<DefaultDatabaseType>()
                        .databaseType(new DefaultDatabaseType("EMBL"))
                        .id("DB123414")
                        .properties(properties)
                        .build();

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
}
