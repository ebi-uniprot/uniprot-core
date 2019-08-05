package org.uniprot.core.uniprot.impl;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.impl.UniProtIdImpl;

import static org.junit.Assert.assertEquals;

public class UniProtIdImplTest {

    @Test
    public void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
        TestHelper.verifyJson(uniprotId);
    }

}
