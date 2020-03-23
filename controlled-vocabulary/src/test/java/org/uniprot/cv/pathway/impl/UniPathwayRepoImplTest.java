package org.uniprot.cv.pathway.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.pathway.UniPathway;
import org.uniprot.cv.pathway.UniPathwayRepo;

class UniPathwayRepoImplTest {
    private static UniPathwayRepo service;

    @BeforeAll
    static void setup() {
        service = new UniPathwayRepoImpl("unipathway.txt");
    }

    @Test
    void test() {
        String id = "611.743";
        UniPathway result = service.getById(id);
        assertNotNull(result);
    }
}
