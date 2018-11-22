package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtIdImplTest {

    @Test
    public void testUniProtIdImpl() {
        String val ="P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
        TestHelper.verifyJson(uniprotId);
    }

}
