package org.uniprot.cv.subcell.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

import java.util.List;

class SubcellularLocationRepoImplTest {
    private SubcellularLocationRepoImpl service;

    @BeforeEach
    void setUp() {
        String file = "src/test/resources/subcell.txt";
        this.service = new SubcellularLocationRepoImpl(file);
    }

    @Test
    void testGetById() {
        String id = "Acrosome";
        SubcellularLocationEntry location = this.service.getById(id);
        assertNotNull(location);
        assertThat(location.getId(), is("SL-0007"));
    }

    @Test
    void testGetAll() {
        List<SubcellularLocationEntry> locs = this.service.getAll();
        Assertions.assertNotNull(locs);
        Assertions.assertEquals(520, locs.size());
    }
}
