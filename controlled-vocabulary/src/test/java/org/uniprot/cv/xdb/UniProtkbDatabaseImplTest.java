package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprotkb.xdb.UniProtkbDatabase;

class UniProtkbDatabaseImplTest {

    @Test
    void canGetDetail() {
        UniProtDatabaseDetail embl = new UniProtkbDatabaseImpl("EMBL").getUniProtDatabaseDetail();
        assertNotNull(embl);
    }

    @Test
    void sameDbName_shouldBeEqual() {
        UniProtkbDatabase embl = new UniProtkbDatabaseImpl("EMBL");
        UniProtkbDatabase embl2 = new UniProtkbDatabaseImpl("EMBL");

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }

    @Test
    void nullDbName_shouldBeConsiderEqual() {
        UniProtkbDatabase embl = new UniProtkbDatabaseImpl(null);
        UniProtkbDatabase embl2 = new UniProtkbDatabaseImpl(null);

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }
}
