package org.uniprot.core.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;

class CrossReferenceImplTest {

    private List<Property> properties =
            asList(new Property("key1", "value1"), new Property("key2", "value2"));
    private CrossReference<DefaultDatabase> xref =
            new CrossReferenceBuilder<DefaultDatabase>()
                    .database(new DefaultDatabase("EMBL"))
                    .id("DB123414")
                    .propertiesSet(properties)
                    .build();

    @Test
    void testDBCrossReferenceImplStringString() {
        CrossReference<DefaultDatabase> xref =
                new CrossReferenceBuilder<DefaultDatabase>()
                        .database(new DefaultDatabase("EMBL"))
                        .id("DB123414")
                        .build();
        verify(xref, "EMBL", "DB123414", Collections.emptyList());
    }

    @Test
    void testDBCrossReferenceImplStringStringListOfProperty() {
        verify(xref, "EMBL", "DB123414", properties);
    }

    private void verify(
            CrossReference<DefaultDatabase> xref,
            String dbName,
            String id,
            List<Property> properties) {
        assertEquals(dbName, xref.getDatabase().getName());
        assertEquals(id, xref.getId());
        assertEquals(properties, xref.getProperties());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CrossReference<DefaultDatabase> obj = new CrossReferenceImpl<>();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<DefaultDatabase> impl =
                new CrossReferenceImpl<>(new DefaultDatabase("EMBL"), "one", properties);
        CrossReference<DefaultDatabase> obj = CrossReferenceBuilder.from(impl).build();

        assertTrue(impl.hasDatabase());
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
