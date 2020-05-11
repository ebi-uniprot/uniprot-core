package org.uniprot.core.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniProtKBDatabaseAttributeTest {
    private String name;
    private String xmlTag;
    private String uriLink;

    @BeforeEach
    void setUp() {
        String random = UUID.randomUUID().toString();
        this.name = "name-" + random;
        this.xmlTag = "xmlTag-" + random;
        this.uriLink = "uriLink-" + random;
    }

    @Test
    void testCreateObject() {
        UniProtDatabaseAttribute xref =
                createDBXRefTypeAttribute(this.name, this.xmlTag, this.uriLink);
        assertNotNull(xref);
        assertEquals(this.name, xref.getName());
        assertEquals(this.xmlTag, xref.getXmlTag());
        assertEquals(this.uriLink, xref.getUriLink());
    }

    @Test
    void testValueEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        UniProtDatabaseAttribute n2 = createDBXRefTypeAttribute();
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void testRefEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        assertEquals(n1, n1);
        assertEquals(n1.hashCode(), n1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        assertNotEquals(null, n1);
    }

    @Test
    void testValueNotEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        this.uriLink = null;
        UniProtDatabaseAttribute n2 = createDBXRefTypeAttribute();
        assertNotEquals(n1, n2);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtDatabaseAttribute obj = new UniProtDatabaseAttribute();
        assertNotNull(obj);
    }

    private UniProtDatabaseAttribute createDBXRefTypeAttribute() {
        return createDBXRefTypeAttribute(this.name, this.xmlTag, this.uriLink);
    }

    static UniProtDatabaseAttribute createDBXRefTypeAttribute(
            String name, String xmlTag, String uriLink) {
        return new UniProtDatabaseAttribute(name, xmlTag, uriLink);
    }
}
