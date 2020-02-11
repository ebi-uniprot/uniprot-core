package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;

class UniProtXDbTypeImplTest {

    @Test
    void canGetDetail() {
        UniProtXDbTypeDetail embl = new UniProtXDbTypeImpl("EMBL").getDetail();
        assertNotNull(embl);
    }

    @Test
    void sameDbName_shouldBeEqual() {
        UniProtXDbType embl = new UniProtXDbTypeImpl("EMBL");
        UniProtXDbType embl2 = new UniProtXDbTypeImpl("EMBL");

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }

    @Test
    void nullDbName_shouldBeConsiderEqual() {
        UniProtXDbType embl = new UniProtXDbTypeImpl(null);
        UniProtXDbType embl2 = new UniProtXDbTypeImpl(null);

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }
}
