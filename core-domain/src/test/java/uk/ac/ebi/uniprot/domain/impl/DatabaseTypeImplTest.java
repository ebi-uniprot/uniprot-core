package uk.ac.ebi.uniprot.domain.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.TestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTypeImplTest {

	@Test
	void test() {
		 DatabaseType type = new  DefaultDatabaseType("EMBL");
		 assertEquals("EMBL", type.getName());
		 TestHelper.verifyJson(type);
	}

}
