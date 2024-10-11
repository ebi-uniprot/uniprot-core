package org.uniprot.cv.xdb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.cv.common.CVSystemProperties;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniProtKBCrossReferenceDisplayOrderTest {
    @BeforeAll
    static void setSystemProperty(){
        System.setProperty(CVSystemProperties.DR_ORD_LOCATION,"src/test/resources/xdb/dr_ord");
    }

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
