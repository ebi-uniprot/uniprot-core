package org.uniprot.cv.pathway.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.pathway.UniPathway;
import org.uniprot.cv.pathway.UniPathwayService;

class UniPathwayServiceImplTest {
    private static UniPathwayService service;

    @BeforeAll
    static void setup() {
        service = new UniPathwayServiceImpl("unipathway.txt");
    }

    @Test
    void test() {
        String id = "611.743";
        UniPathway result = service.getById(id);
        assertNotNull(result);
    }
}
