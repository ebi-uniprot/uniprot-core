package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineConverter;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.ac.UniProtkbAcLineObject;
import org.uniprot.core.uniprotkb.UniProtkbAccession;

class AcLineConverterTest {
    private AcLineConverter converter = new AcLineConverter();

    @Test
    void testConverter() {
        // val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
        AcLineObject acObj = new AcLineObject();
        acObj.primaryAcc = "Q6GZX4";
        acObj.secondaryAcc.add("Q6GZX5");
        acObj.secondaryAcc.add("Q6GZX6");

        UniProtkbAcLineObject uniObj = converter.convert(acObj);
        assertEquals("Q6GZX4", uniObj.getPrimaryAccession().getValue());
        assertEquals(2, uniObj.getSecondAccessions().size());
        testSecondAccIn("Q6GZX5", uniObj);
        testSecondAccIn("Q6GZX6", uniObj);
    }

    @Test
    void testConverter2() {
        // val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
        AcLineObject acObj = new AcLineObject();
        acObj.primaryAcc = "Q6GZX4";

        UniProtkbAcLineObject uniObj = converter.convert(acObj);
        assertEquals("Q6GZX4", uniObj.getPrimaryAccession().getValue());
        assertEquals(0, uniObj.getSecondAccessions().size());
    }

    private void testSecondAccIn(String val, UniProtkbAcLineObject uniObj) {
        boolean found = false;
        for (UniProtkbAccession scObj : uniObj.getSecondAccessions()) {
            if (val.equals(scObj.getValue())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
}
