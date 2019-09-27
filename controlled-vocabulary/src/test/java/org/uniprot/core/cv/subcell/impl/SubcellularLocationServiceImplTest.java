package org.uniprot.core.cv.subcell.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubcellularLocationServiceImplTest {
	private SubcellularLocationServiceImpl service;

	@BeforeEach
	void setUp(){
		String file ="src/test/resources/subcell.txt";
		this.service = new SubcellularLocationServiceImpl(file);
	}

	@Test
	void testGetById() {
		String id ="Acrosome";
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
