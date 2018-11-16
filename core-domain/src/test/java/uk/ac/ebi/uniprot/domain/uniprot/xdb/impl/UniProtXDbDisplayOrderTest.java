package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbDisplayOrder;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypeDetail;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypes;

public class UniProtXDbDisplayOrderTest {

    @Test
    public void testCreateUniProtXDbDisplayOrder() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(dorder.getOrderedDatabases().contains(UniProtXDbTypes.INSTANCE.getType("EMBL")));
    }
    @Test
    public void testGetOrderedDatabases() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
        List<UniProtXDbTypeDetail> dbtypes =dorder.getOrderedDatabases();
       
        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtXDbTypes.INSTANCE.getType("EMBL"), dbtypes.get(0));
        assertEquals(UniProtXDbTypes.INSTANCE.getType("CCDS"), dbtypes.get(1));
    }

}
