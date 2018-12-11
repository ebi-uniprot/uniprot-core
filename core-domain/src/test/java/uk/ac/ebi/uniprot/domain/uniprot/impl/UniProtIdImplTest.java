package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;

import static org.junit.Assert.assertEquals;

public class UniProtIdImplTest {

    @Test
    public void testUniProtIdImpl() {
        String val ="P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
        TestHelper.verifyJson(uniprotId);
    }

}
