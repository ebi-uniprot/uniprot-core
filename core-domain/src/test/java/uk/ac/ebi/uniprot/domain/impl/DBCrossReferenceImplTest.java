package uk.ac.ebi.uniprot.domain.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;

class DBCrossReferenceImplTest {

//	@Test
//	void testDBCrossReferenceImpl() {
//		DBCrossReferenceImpl xref = new DBCrossReferenceImpl();
//		xref.setDatabaseName("EMBL");
//		xref.setId("DB123414");
//		verify(xref, "EMBL", "DB123414", Collections.emptyList());
//	}

	private void verify(DBCrossReferenceImpl xref, String dbName, String id,
			List<Property> properties) {
		assertEquals(dbName, xref.getDatabaseName());
		assertEquals(id, xref.getId());
		assertEquals(properties, xref.getProperties());
		TestHelper.verifyJson(xref);
	}
	@Test
	void testDBCrossReferenceImplStringString() {
		DBCrossReferenceImpl xref = new DBCrossReferenceImpl("EMBL", "DB123414");
		verify(xref, "EMBL", "DB123414",Collections.emptyList());
	}

	@Test
	void testDBCrossReferenceImplStringStringListOfProperty() {
		List<Property> properties =new ArrayList<>();
		properties.add(new Property("key1", "value1"));
		properties.add(new Property("key2", "value2"));
		
		DBCrossReferenceImpl xref = new DBCrossReferenceImpl("EMBL", "DB123414", properties);
		
		verify(xref, "EMBL", "DB123414",properties);
	}

}
