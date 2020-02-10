package org.uniprot.cv.subcell.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.cv.subcell.SubcellularLocationEntry;

class SubcellularLocationServiceImplTest {
    private SubcellularLocationServiceImpl service;

    @BeforeEach
    void setUp() {
        String file = "src/test/resources/subcell.txt";
        this.service = new SubcellularLocationServiceImpl(file);
    }

    @Test
    void testGetById() {
        String id = "Acrosome";
        SubcellularLocationEntry location = this.service.getById(id);
        assertNotNull(location);
        assertThat(location.getAccession(), is("SL-0007"));
    }

    @Test
    void testGetAll() {
        List<SubcellularLocationEntry> locs = this.service.getAll();
        Assertions.assertNotNull(locs);
        Assertions.assertEquals(520, locs.size());
    }
}
