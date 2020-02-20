package org.uniprot.core.cv.keyword.builder;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordEntryKeyword;

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
        KeywordEntryKeyword kw = createKeyword(this.id, this.accession);
        Assertions.assertEquals(this.id, kw.getId());
        Assertions.assertEquals(this.accession, kw.getAccession());
    }

    @Test
    void testValueEqual() {
        KeywordEntryKeyword kw1 = createKeyword(this.id, this.accession);
        KeywordEntryKeyword kw2 = createKeyword(this.id, this.accession);
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testRefEqual() {
        KeywordEntryKeyword kw1 = createKeyword(this.id, this.accession);
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        KeywordEntryKeyword kw = createKeyword(this.id, this.accession);
        Assertions.assertFalse(kw.equals(null));
    }

    private KeywordEntryKeyword createKeyword(String id, String accession) {
        return new KeywordEntryKeywordBuilder().id(id).accession(accession).build();
    }
}
