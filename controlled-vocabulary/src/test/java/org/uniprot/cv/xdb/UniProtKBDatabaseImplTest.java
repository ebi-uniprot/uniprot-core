package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

class UniProtKBDatabaseImplTest {

    @Test
    void canGetDetail() {
        UniProtDatabaseDetail embl = new UniProtKBDatabaseImpl("EMBL").getUniProtDatabaseDetail();
        assertNotNull(embl);
    }

    @Test
    void sameDbName_shouldBeEqual() {
        UniProtKBDatabase embl = new UniProtKBDatabaseImpl("EMBL");
        UniProtKBDatabase embl2 = new UniProtKBDatabaseImpl("EMBL");

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }

    @Test
    void nullDbName_shouldBeConsiderEqual() {
        UniProtKBDatabase embl = new UniProtKBDatabaseImpl(null);
        UniProtKBDatabase embl2 = new UniProtKBDatabaseImpl(null);

        assertTrue(embl.equals(embl2) && embl2.equals(embl));
        assertEquals(embl.hashCode(), embl2.hashCode());
    }
}
