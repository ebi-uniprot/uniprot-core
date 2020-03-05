package org.uniprot.core.cv.xdb;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UniProtDatabaseAttributeTest {
    private String random;
    private String name;
    private String xmlTag;
    private String uriLink;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.name = "name-" + this.random;
        this.xmlTag = "xmlTag-" + this.random;
        this.uriLink = "uriLink-" + this.random;
    }

    @Test
    void testCreateObject() {
        UniProtDatabaseAttribute xref =
                createDBXRefTypeAttribute(this.name, this.xmlTag, this.uriLink);
        Assertions.assertNotNull(xref);
        Assertions.assertEquals(this.name, xref.getName());
        Assertions.assertEquals(this.xmlTag, xref.getXmlTag());
        Assertions.assertEquals(this.uriLink, xref.getUriLink());
    }

    @Test
    void testValueEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        UniProtDatabaseAttribute n2 = createDBXRefTypeAttribute();
        Assertions.assertTrue(n1.equals(n2));
        Assertions.assertTrue(n1.hashCode() == n2.hashCode());
    }

    @Test
    void testRefEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        Assertions.assertTrue(n1.equals(n1));
        Assertions.assertTrue(n1.hashCode() == n1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        Assertions.assertFalse(n1.equals(null));
    }

    @Test
    void testValueNotEqual() {
        UniProtDatabaseAttribute n1 = createDBXRefTypeAttribute();
        this.uriLink = null;
        UniProtDatabaseAttribute n2 = createDBXRefTypeAttribute();
        Assertions.assertFalse(n1.equals(n2));
    }

    private UniProtDatabaseAttribute createDBXRefTypeAttribute() {
        return createDBXRefTypeAttribute(this.name, this.xmlTag, this.uriLink);
    }

    public static UniProtDatabaseAttribute createDBXRefTypeAttribute(
            String name, String xmlTag, String uriLink) {
        return new UniProtDatabaseAttribute(name, xmlTag, uriLink);
    }
}