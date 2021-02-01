package org.uniprot.core.flatfile.tool.ca;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 05-Oct-2020
 */
class CatalyticActivityTest {

    @Test
    void testCatalyticActivity() {
        String rheaUn = "value1";
        String text = "value 2";
        List<String> reactantIds = Arrays.asList("val1", "val2");
        List<String> ecs = Arrays.asList("ec1", "ec2");
        String rheaLr = "value 3";
        String rheaRl = "value 4";

        CatalyticActivity ca =
                new CatalyticActivity(rheaUn, text, reactantIds, ecs, rheaLr, rheaRl);
        assertEquals(rheaUn, ca.getRheaUn());
        assertEquals(text, ca.getText());
        assertEquals(reactantIds, ca.getReactantIds());
        assertEquals(ecs, ca.getEcs());
        assertEquals(rheaLr, ca.getRheaLr());
        assertEquals(rheaRl, ca.getRheaRl());

        CatalyticActivity ca2 =
                new CatalyticActivity(rheaUn, text, reactantIds, ecs, rheaLr, rheaRl);
        assertEquals(ca, ca2);
        assertEquals(ca.hashCode(), ca2.hashCode());
    }
}
