package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbDisplayOrder;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtXDbDisplayOrderTest {

    @Test
    public void testCreateUniProtXDbDisplayOrder() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.getInstance();
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(dorder.getOrderedDatabases().contains(DatabaseType.EMBL));
    }
    @Test
    public void testGetOrderedDatabases() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.getInstance();
        List<DatabaseType> dbtypes =dorder.getOrderedDatabases();
       
        assertFalse(dbtypes.isEmpty());
        assertEquals(DatabaseType.EMBL, dbtypes.get(0));
        assertEquals(DatabaseType.CCDS, dbtypes.get(1));
    }

}
