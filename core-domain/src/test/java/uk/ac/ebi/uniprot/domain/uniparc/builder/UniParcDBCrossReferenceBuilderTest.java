package uk.ac.ebi.uniprot.domain.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
 */

class UniParcDBCrossReferenceBuilderTest {

	@Test
	void testVersionI() {
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(2).build();
		assertEquals(2, xref.getVersionI());
	}

	@Test
	void testVersion() {
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(2).version(5).build();
		assertEquals(2, xref.getVersionI());
		assertEquals(5, xref.getVersion().intValue());
	}

	@Test
	void testActive() {
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3).version(7).active(true).build();
		assertEquals(3, xref.getVersionI());
		assertEquals(7, xref.getVersion().intValue());
		assertTrue(xref.isActive());
	}

	@Test
	void testCreated() {
		LocalDate created = LocalDate.of(2017, 5, 17);
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3).version(7).active(false)
				.created(created).build();
		assertEquals(3, xref.getVersionI());
		assertEquals(7, xref.getVersion().intValue());
		assertFalse(xref.isActive());
		assertEquals(created, xref.getCreated());
	}

	@Test
	void testLastUpdated() {
		LocalDate created = LocalDate.of(2017, 5, 17);
		LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.SWISSPROT).id("P12345").version(7).active(true).created(created)
				.lastUpdated(lastUpdated).build();
		assertEquals(3, xref.getVersionI());
		assertEquals(7, xref.getVersion().intValue());
		assertTrue(xref.isActive());
		assertEquals(created, xref.getCreated());
		assertEquals(lastUpdated, xref.getLastUpdated());
		assertEquals(UniParcDatabaseType.SWISSPROT, xref.getDatabaseType());
		assertEquals("P12345", xref.getId());

	}

	@Test
	void testProperties() {
		LocalDate created = LocalDate.of(2017, 5, 17);
		LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
		List<Property> properties = new ArrayList<>();
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.SWISSPROT).id("P12345").version(7).active(true).created(created)
				.lastUpdated(lastUpdated).properties(properties).build();
		assertEquals(3, xref.getVersionI());
		assertEquals(7, xref.getVersion().intValue());
		assertTrue(xref.isActive());
		assertEquals(created, xref.getCreated());
		assertEquals(lastUpdated, xref.getLastUpdated());
		assertEquals(UniParcDatabaseType.SWISSPROT, xref.getDatabaseType());
		assertEquals("P12345", xref.getId());
		assertEquals(properties, xref.getProperties());
	}

	@Test
	void testFromUniParcDBCrossReference() {
		LocalDate created = LocalDate.of(2017, 5, 17);
		LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
		List<Property> properties = new ArrayList<>();
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.SWISSPROT).id("P12345").version(7).active(true).created(created)
				.lastUpdated(lastUpdated).properties(properties).build();
		assertEquals(3, xref.getVersionI());
		assertEquals(7, xref.getVersion().intValue());
		assertTrue(xref.isActive());
		assertEquals(created, xref.getCreated());
		assertEquals(lastUpdated, xref.getLastUpdated());
		assertEquals(UniParcDatabaseType.SWISSPROT, xref.getDatabaseType());
		assertEquals("P12345", xref.getId());
		assertEquals(properties, xref.getProperties());

		UniParcDBCrossReference newXref = new UniParcDBCrossReferenceBuilder().from(xref).build();
		assertEquals(xref, newXref);
		UniParcDBCrossReference newXref2 = new UniParcDBCrossReferenceBuilder().from(xref).id("P23456").build();
		assertNotEquals(newXref2, xref);
		assertEquals("P23456", newXref2.getId());
	}

}
