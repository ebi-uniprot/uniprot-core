package org.uniprot.core.uniprot.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.cv.xdb.UniProtXDbTypes;
import org.uniprot.core.uniprot.xdb.UniProtXDbDisplayOrder;

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
        List<UniProtXDbTypeDetail> dbtypes = dorder.getOrderedDatabases();

        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtXDbTypes.INSTANCE.getType("EMBL"), dbtypes.get(0));
        assertEquals(UniProtXDbTypes.INSTANCE.getType("CCDS"), dbtypes.get(1));
    }
}
