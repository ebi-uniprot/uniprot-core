package org.uniprot.core.uniprot.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;

class UniProtXDbTypeTest {

    @Test
    void canGetDetail() {
        UniProtXDbTypeDetail embl = new UniProtXDbType("EMBL").getDetail();
        assertNotNull(embl);
    }

    @Test
    void sameDbName_shouldBeEqual() {
        UniProtXDbType embl = new UniProtXDbType("EMBL");
        UniProtXDbType embl2 = new UniProtXDbType("EMBL");

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }

    @Test
    void nullDbName_shouldBeConsiderEqual() {
        UniProtXDbType embl = new UniProtXDbType(null);
        UniProtXDbType embl2 = new UniProtXDbType(null);

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }
}
