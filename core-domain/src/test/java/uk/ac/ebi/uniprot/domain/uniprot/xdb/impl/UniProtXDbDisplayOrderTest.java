package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtXDbDisplayOrderTest {

    @Test
    public void testGetOrderedDatabases() {
        UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.getInstance();
        assertFalse(dorder.getOrderedDatabases().isEmpty());
        assertTrue(dorder.getOrderedDatabases().contains(DatabaseType.EMBL));
    }

}
