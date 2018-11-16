package uk.ac.ebi.uniprot.domain.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.TestHelper;

class DatabaseTypeImplTest {

	@Test
	void test() {
		 DatabaseType type = new  DefaultDatabaseType("EMBL");
		 assertEquals("EMBL", type.getName());
		 TestHelper.verifyJson(type);
	}

}
