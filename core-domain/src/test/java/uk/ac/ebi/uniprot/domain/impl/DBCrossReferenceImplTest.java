package uk.ac.ebi.uniprot.domain.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private void verify(DBCrossReference<DefaultDatabaseType> xref, String dbName, String id,
                        List<Property> properties) {
        assertEquals(dbName, xref.getDatabaseType().getName());
        assertEquals(id, xref.getId());
        assertEquals(properties, xref.getProperties());
        TestHelper.verifyJson(xref);
    }
}
