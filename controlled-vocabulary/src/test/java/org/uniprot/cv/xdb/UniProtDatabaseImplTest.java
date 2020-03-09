package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;

class UniProtDatabaseImplTest {

    @Test
    void canGetDetail() {
        UniProtDatabaseDetail embl = new UniProtDatabaseImpl("EMBL").getUniProtDatabaseDetail();
        assertNotNull(embl);
    }

    @Test
    void sameDbName_shouldBeEqual() {
        UniProtDatabase embl = new UniProtDatabaseImpl("EMBL");
        UniProtDatabase embl2 = new UniProtDatabaseImpl("EMBL");

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }

    @Test
    void nullDbName_shouldBeConsiderEqual() {
        UniProtDatabase embl = new UniProtDatabaseImpl(null);
        UniProtDatabase embl2 = new UniProtDatabaseImpl(null);

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }
}
