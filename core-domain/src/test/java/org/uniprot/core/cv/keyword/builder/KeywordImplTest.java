package org.uniprot.core.cv.keyword.builder;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordId;

public class KeywordImplTest {

    private String random;
    private String id;
    private String accession;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.id = "id-" + this.random;
        this.accession = "accession-" + this.random;
    }

    @Test
    void testCreateKeyword() {
        KeywordId kw = createKeyword(this.id, this.accession);
        Assertions.assertEquals(this.id, kw.getName());
        Assertions.assertEquals(this.accession, kw.getId());
    }

    @Test
    void testValueEqual() {
        KeywordId kw1 = createKeyword(this.id, this.accession);
        KeywordId kw2 = createKeyword(this.id, this.accession);
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testRefEqual() {
        KeywordId kw1 = createKeyword(this.id, this.accession);
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        KeywordId kw = createKeyword(this.id, this.accession);
        Assertions.assertFalse(kw.equals(null));
    }

    private KeywordId createKeyword(String id, String accession) {
        return new KeywordIdBuilder().id(id).accession(accession).build();
    }
}
