package org.uniprot.core.flatfile.tool.ca;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CatalyticActivityFileRepositoryTest {
	private static CatalyticActivityFileRepository repository;
	@BeforeAll
	static void setup() {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("catalyticactivity/catalytic_activity.tsv");
		repository =new  CatalyticActivityFileRepository(is);
	}
	@Test
	void testGetByRheaIdValid() {
		String rheaId = "RHEA:10004";
		CatalyticActivity catalyticActivity = repository.getByRheaId(rheaId);
		String text ="benzyl isothiocyanate = benzyl thiocyanate";
	
		assertNotNull(catalyticActivity);
		assertEquals(rheaId, catalyticActivity.getRheaUn());
		assertEquals(text, catalyticActivity.getText());

	}
	@Test
	void testGetByRheaIdInValid() {
		String rheaId = "RHEA:0004";
		CatalyticActivity catalyticActivity = repository.getByRheaId(rheaId);
		assertNull(catalyticActivity);

	}
	
	@Test
	void testGetByOldTextValid() {
		String text ="Erythro-3-hydroxy-L-aspartate = oxaloacetate + NH(3).";
		CatalyticActivity catalyticActivity = repository.getByOldText(text);
		assertNotNull(catalyticActivity);
		assertEquals(text, catalyticActivity.getText());
	}
	
	@Test
	void testGetByOldTextInValid() {
		String text ="Erythro-3-hydroxy-L-aspartate = oxaloacetate + NH(3)";
		CatalyticActivity catalyticActivity = repository.getByOldText(text);
		assertNull(catalyticActivity);

	}
	

}
