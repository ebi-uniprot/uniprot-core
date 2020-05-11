package org.uniprot.core.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UniProtKBDatabaseDetailTest {
    private String name;
    private String displayName;
    private UniProtDatabaseCategory category;
    private String uriLink;
    private List<UniProtDatabaseAttribute> attributes;

    @BeforeEach
    void setUp() {
        String random = UUID.randomUUID().toString();
        this.name = "name-" + random;
        this.displayName = "displayName-" + random;
        this.category = UniProtDatabaseCategory.CHEMISTRY;
        this.uriLink = "uriLink-" + random;
        this.attributes =
                IntStream.range(0, 3)
                        .mapToObj(
                                i ->
                                        UniProtKBDatabaseAttributeTest.createDBXRefTypeAttribute(
                                                this.name, this.displayName, this.uriLink))
                        .collect(Collectors.toList());
    }

    @Test
    void testCreateObject() {
        UniProtDatabaseDetail dbType = createUniProtDatabaseDetail(false);
        assertNotNull(dbType);
        assertEquals(this.name, dbType.getName());
        assertEquals(this.displayName, dbType.getDisplayName());
        assertEquals(this.category, dbType.getCategory());
        assertEquals(this.uriLink, dbType.getUriLink());
        assertEquals(1, dbType.getAttributes().size());
        assertEquals("Description", dbType.getAttributes().get(0).getName());
        assertEquals("description", dbType.getAttributes().get(0).getXmlTag());
        Assertions.assertNull(dbType.getAttributes().get(0).getUriLink());
    }

    @Test
    void testCreateObjectWithAttrib() {
        UniProtDatabaseDetail dbType = createUniProtDatabaseDetail(true);
        assertNotNull(dbType);
        assertEquals(this.name, dbType.getName());
        assertEquals(this.displayName, dbType.getDisplayName());
        assertEquals(this.category, dbType.getCategory());
        assertEquals(this.uriLink, dbType.getUriLink());
        assertEquals(3, dbType.getAttributes().size());
    }

    @Test
    void testValueEqual() {
        UniProtDatabaseDetail n1 = createUniProtDatabaseDetail(false);
        UniProtDatabaseDetail n2 = createUniProtDatabaseDetail(false);
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void testRefEqual() {
        UniProtDatabaseDetail n1 = createUniProtDatabaseDetail(true);
        assertEquals(n1, n1);
        assertEquals(n1.hashCode(), n1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        UniProtDatabaseDetail n1 = createUniProtDatabaseDetail(true);
        assertNotEquals(null, n1);
    }

    @Test
    void testValueNotEqual() {
        UniProtDatabaseDetail n1 = createUniProtDatabaseDetail(false);
        this.uriLink = null;
        UniProtDatabaseDetail n2 = createUniProtDatabaseDetail(false);
        assertNotEquals(n1, n2);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtDatabaseDetail obj = new UniProtDatabaseDetail();
        assertNotNull(obj);
        assertEquals(1, obj.getAttributes().size());
        assertNull(obj.getLinkedReason());
        assertFalse(obj.isImplicit());
    }

    private UniProtDatabaseDetail createUniProtDatabaseDetail(boolean passAttribute) {
        if (passAttribute) {
            return createUniProtDatabaseDetail(
                    this.name, this.displayName, this.category, this.uriLink, this.attributes);
        } else {
            return createUniProtDatabaseDetail(
                    this.name, this.displayName, this.category, this.uriLink, null);
        }
    }

    static UniProtDatabaseDetail createUniProtDatabaseDetail(
            String name,
            String displayName,
            UniProtDatabaseCategory category,
            String uriLink,
            List<UniProtDatabaseAttribute> attributes) {
        return new UniProtDatabaseDetail(
                name, displayName, category, uriLink, attributes, false, null);
    }
}
