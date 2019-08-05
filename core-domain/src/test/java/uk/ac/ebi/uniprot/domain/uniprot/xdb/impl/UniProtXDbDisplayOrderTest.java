package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import org.junit.Test;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.cv.xdb.UniProtXDbTypes;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbDisplayOrder;

import java.util.List;

import static org.junit.Assert.*;

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
        List<UniProtXDbTypeDetail> dbtypes = dorder.getOrderedDatabases();

        assertFalse(dbtypes.isEmpty());
        assertEquals(UniProtXDbTypes.INSTANCE.getType("EMBL"), dbtypes.get(0));
        assertEquals(UniProtXDbTypes.INSTANCE.getType("CCDS"), dbtypes.get(1));
    }

}
