package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbDisplayOrder;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypes;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtXDbDisplayOrderTest {

    @Test
    public void testCreateUniProtXDbDisplayOrder() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(dorder.getOrderedDatabases().contains(UniProtXDbTypes.INSTANCE.getType("EMBL").get()));
    }
    @Test
    public void testGetOrderedDatabases() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        List<UniProtXDbType> dbtypes =dorder.getOrderedDatabases();
       
        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtXDbTypes.INSTANCE.getType("EMBL").get(), dbtypes.get(0));
        assertEquals(UniProtXDbTypes.INSTANCE.getType("CCDS").get(), dbtypes.get(1));
    }

}
