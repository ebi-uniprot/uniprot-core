package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.impl.ValueImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ValueImplTest {


    @Test
    public void testGetValue() {
        String val = "Some Value";
        ValueImpl valImpl = new ValueImpl(val);
        assertEquals(val, valImpl.getValue());
        ValueImpl valImpl2 = new ValueImpl(val);
        assertEquals(valImpl, valImpl2);
        ValueImpl valImpl3 = new ValueImpl("AnotherValue");
        assertNotEquals(valImpl, valImpl3);
    }

}
