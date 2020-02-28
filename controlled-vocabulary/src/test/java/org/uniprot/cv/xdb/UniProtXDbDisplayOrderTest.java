package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

class UniProtXDbDisplayOrderTest {

    @Test
    void testCreateUniProtXDbDisplayOrder() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(dorder.getOrderedDatabases().contains(UniProtXDbTypes.INSTANCE.getType("EMBL")));
    }

    @Test
    void testGetOrderedDatabases() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        List<UniProtDatabaseDetail> dbtypes = dorder.getOrderedDatabases();

        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtXDbTypes.INSTANCE.getType("EMBL"), dbtypes.get(0));
        assertEquals(UniProtXDbTypes.INSTANCE.getType("CCDS"), dbtypes.get(1));
    }
}
