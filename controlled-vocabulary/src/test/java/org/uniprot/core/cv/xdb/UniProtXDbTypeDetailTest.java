package org.uniprot.core.cv.xdb;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UniProtXDbTypeDetailTest {
    private String random;
    private String name;
    private String displayName;
    private DatabaseCategory category;
    private String uriLink;
    private List<DBXRefTypeAttribute> attributes;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.name = "name-" + this.random;
        this.displayName = "displayName-" + this.random;
        this.category = DatabaseCategory.CHEMISTRY;
        this.uriLink = "uriLink-" + this.random;
        this.attributes =
                IntStream.range(0, 3)
                        .mapToObj(
                                i ->
                                        DBXRefTypeAttributeTest.createDBXRefTypeAttribute(
                                                this.name, this.displayName, this.uriLink))
                        .collect(Collectors.toList());
    }

    @Test
    void testCreateObject() {
        UniProtXDbTypeDetail dbType = createUniProtXDbTypeDetail(false);
        Assertions.assertNotNull(dbType);
        Assertions.assertEquals(this.name, dbType.getName());
        Assertions.assertEquals(this.displayName, dbType.getDisplayName());
        Assertions.assertEquals(this.category, dbType.getCategory());
        Assertions.assertEquals(this.uriLink, dbType.getUriLink());
        Assertions.assertEquals(1, dbType.getAttributes().size());
        Assertions.assertEquals("Description", dbType.getAttributes().get(0).getName());
        Assertions.assertEquals("description", dbType.getAttributes().get(0).getXmlTag());
        Assertions.assertNull(dbType.getAttributes().get(0).getUriLink());
    }

    @Test
    void testCreateObjectWithAttrib() {
        UniProtXDbTypeDetail dbType = createUniProtXDbTypeDetail(true);
        Assertions.assertNotNull(dbType);
        Assertions.assertEquals(this.name, dbType.getName());
        Assertions.assertEquals(this.displayName, dbType.getDisplayName());
        Assertions.assertEquals(this.category, dbType.getCategory());
        Assertions.assertEquals(this.uriLink, dbType.getUriLink());
        Assertions.assertEquals(3, dbType.getAttributes().size());
    }

    @Test
    void testValueEqual() {
        UniProtXDbTypeDetail n1 = createUniProtXDbTypeDetail(false);
        UniProtXDbTypeDetail n2 = createUniProtXDbTypeDetail(false);
        Assertions.assertTrue(n1.equals(n2));
        Assertions.assertTrue(n1.hashCode() == n2.hashCode());
    }

    @Test
    void testRefEqual() {
        UniProtXDbTypeDetail n1 = createUniProtXDbTypeDetail(true);
        Assertions.assertTrue(n1.equals(n1));
        Assertions.assertTrue(n1.hashCode() == n1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        UniProtXDbTypeDetail n1 = createUniProtXDbTypeDetail(true);
        Assertions.assertFalse(n1.equals(null));
    }

    @Test
    void testValueNotEqual() {
        UniProtXDbTypeDetail n1 = createUniProtXDbTypeDetail(false);
        this.uriLink = null;
        UniProtXDbTypeDetail n2 = createUniProtXDbTypeDetail(false);
        Assertions.assertFalse(n1.equals(n2));
    }

    private UniProtXDbTypeDetail createUniProtXDbTypeDetail(boolean passAttribute) {
        if (passAttribute) {
            return createUniProtXDbTypeDetail(
                    this.name, this.displayName, this.category, this.uriLink, this.attributes);
        } else {
            return createUniProtXDbTypeDetail(
                    this.name, this.displayName, this.category, this.uriLink, null);
        }
    }

    public static UniProtXDbTypeDetail createUniProtXDbTypeDetail(
            String name,
            String displayName,
            DatabaseCategory category,
            String uriLink,
            List<DBXRefTypeAttribute> attributes) {
        return new UniProtXDbTypeDetail(name, displayName, category, uriLink, attributes);
    }
}