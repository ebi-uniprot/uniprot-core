package uk.ac.ebi.uniprot.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcDBCrossReferenceBuilder;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
*/

class UniParcXrefMapTest {

	@Test
	void testAttributeValues() {
		List<UniParcDBCrossReference>  xrefs = create();
		UniParcXrefMap xrefMap = new UniParcXrefMap(xrefs);
		Map<String, String> result= xrefMap.attributeValues();
		assertEquals(6, result.size());
		assertEquals(";", result.get("gene"));
		assertEquals("some pname;some pname2", result.get("protein"));
		assertEquals("P12345; P12347.2 (obsolete)", result.get("accession"));
		assertEquals("UP00000564:chromosome 1", result.get("proteome"));
		assertEquals("2015-01-11", result.get("first_seen"));
		assertEquals("2018-02-07", result.get("last_seen"));
	}

	@Test
	void testFields() {
		List<String> fields =Arrays.asList( "gene", "protein", "proteome", "accession", "first_seen", "last_seen");
		assertEquals(UniParcXrefMap.FIELDS, fields);
	}
	@Test
	void testContains() {
		List<String> fields =Arrays.asList( "gene", "upi", "CDD");
		assertTrue (UniParcXrefMap.contains(fields));
	}
	
	List<UniParcDBCrossReference> create() {
		LocalDate created = LocalDate.of(2017, 5, 17);
		LocalDate lastUpdated = LocalDate.of(2018, 2, 7);
		List<Property> properties = new ArrayList<>();
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
	//	properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.SWISSPROT).id("P12345").version(7).active(true).created(created)
				.lastUpdated(lastUpdated).properties(properties).build();
		
		
		LocalDate created2 = LocalDate.of(2015, 1, 11);
		LocalDate lastUpdated2 = LocalDate.of(2017, 2, 27);
		List<Property> properties2 = new ArrayList<>();
		properties2.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname2"));
		properties2.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEOME_ID, "UP00000564"));
		properties2.add(new Property(UniParcDBCrossReference.PROPERTY_COMPONENT, "chromosome 1"));
		UniParcDBCrossReference xref2 = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.TREMBL).id("P12347").version(2).active(false).created(created2)
				.lastUpdated(lastUpdated2).properties(properties2).build();
		
		return Arrays.asList(xref, xref2);
		
	}
}

