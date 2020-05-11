package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

class UniProtKBCrossReferenceDisplayOrderTest {

    @Test
    void testCreateUniProtXDbDisplayOrder() {
        UniProtCrossReferenceDisplayOrder dorder = UniProtCrossReferenceDisplayOrder.INSTANCE;
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(
                dorder.getOrderedDatabases()
                        .contains(UniProtDatabaseTypes.INSTANCE.getDbTypeByName("EMBL")));
    }

    @Test
    void testGetOrderedDatabases() {
        UniProtCrossReferenceDisplayOrder dorder = UniProtCrossReferenceDisplayOrder.INSTANCE;
        List<UniProtDatabaseDetail> dbtypes = dorder.getOrderedDatabases();

        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtDatabaseTypes.INSTANCE.getDbTypeByName("EMBL"), dbtypes.get(0));
        assertEquals(UniProtDatabaseTypes.INSTANCE.getDbTypeByName("CCDS"), dbtypes.get(1));
    }
}
