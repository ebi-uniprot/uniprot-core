package uk.ac.ebi.uniprot.domain.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.TestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBCrossReferenceImplTest {

//	@Test
//	void testDBCrossReferenceImpl() {
//		DBCrossReferenceImpl xref = new DBCrossReferenceImpl();
//		xref.setDatabaseName("EMBL");
//		xref.setId("DB123414");
//		verify(xref, "EMBL", "DB123414", Collections.emptyList());
//	}

    @Test
    void testDBCrossReferenceImplStringString() {
        DBCrossReferenceImpl<DefaultDatabaseType> xref = new DBCrossReferenceImpl<>(new DefaultDatabaseType("EMBL"), "DB123414");
        verify(xref, "EMBL", "DB123414", Collections.emptyList());
    }

    @Test
    void testDBCrossReferenceImplStringStringListOfProperty() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("key1", "value1"));
        properties.add(new Property("key2", "value2"));

        DBCrossReferenceImpl<DefaultDatabaseType> xref = new DBCrossReferenceImpl<>(new DefaultDatabaseType("EMBL"), "DB123414", properties);

        verify(xref, "EMBL", "DB123414", properties);
    }

    private void verify(DBCrossReferenceImpl<DefaultDatabaseType> xref, String dbName, String id,
                        List<Property> properties) {
        assertEquals(dbName, xref.getDatabaseType().getName());
        assertEquals(id, xref.getId());
        assertEquals(properties, xref.getProperties());
        TestHelper.verifyJson(xref);
    }

}
