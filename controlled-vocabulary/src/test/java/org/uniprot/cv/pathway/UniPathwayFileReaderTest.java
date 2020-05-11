package org.uniprot.cv.pathway;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.pathway.UniPathway;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class UniPathwayFileReaderTest {
    private final UniPathwayFileReader reader = new UniPathwayFileReader();

    @Test
    void test() {
        String filename = "src/main/resources/unipathway.txt";
        List<UniPathway> unipathwayList = reader.parse(filename);
        assertNotNull(unipathwayList);
        assertFalse(unipathwayList.isEmpty());
        String id = "611.743";
        String text = "Alcohol metabolism; butanol biosynthesis";
        Optional<UniPathway> opUP =
                unipathwayList.stream().filter(val -> val.getId().equals(id)).findFirst();
        assertTrue(opUP.isPresent());
        String name = opUP.map(val -> val.getName()).orElse("");
        assertEquals(text, name);
        String id2 = "411";

        opUP = unipathwayList.stream().filter(val -> val.getId().equals(id2)).findFirst();

        assertTrue(opUP.isPresent());
        List<UniPathway> result =
                opUP.map(val -> val.getChildren()).orElse(Collections.emptyList());
        assertFalse(result.isEmpty());
    }
}
