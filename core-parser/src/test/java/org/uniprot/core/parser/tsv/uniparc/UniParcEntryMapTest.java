package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.parser.tsv.uniparc.UniParcEntryMap;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;
import org.uniprot.core.uniparc.builder.UniParcEntryBuilder;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
*/

class UniParcEntryMapTest {


	@Test
	void testGetDataOrganism() {
		UniParcEntry entry = create();
		  List<String> fields = Arrays.asList("upi", "organism", "organism_id");
		  UniParcEntryMap entryMap = new UniParcEntryMap(entry, fields);
		  List<String> result = entryMap.getData();
		
		  assertEquals(fields.size(), result.size());
		  verify("UPI0000083A08", 0, result);
		  verify("Homo sapiens; MOUSE", 1, result);
		  verify("9606; 10090", 2, result);
	}
	
	@Test
	void testGetDataProteinGene() {
		UniParcEntry entry = create();
		  List<String> fields = Arrays.asList("upi", "protein", "gene","accession");
		  UniParcEntryMap entryMap = new UniParcEntryMap(entry, fields);
		  List<String> result = entryMap.getData();
		  assertEquals(fields.size(), result.size());
		  verify("UPI0000083A08", 0, result);
		  verify("some pname;some pname", 1, result);
		  verify("some gname;", 2, result);
		  verify("P12345; P52346", 3, result);
	}
	@Test
	void testGetDate() {
		UniParcEntry entry = create();
		  List<String> fields = Arrays.asList("upi", "first_seen", "last_seen");
		  UniParcEntryMap entryMap = new UniParcEntryMap(entry, fields);
		  List<String> result = entryMap.getData();
		  assertEquals(fields.size(), result.size());
		  verify("UPI0000083A08", 0, result);
		  verify("2017-02-12", 1, result);
		  verify("2017-04-23", 2, result);
	}
	@Test
	void testGetSequence() {
		UniParcEntry entry = create();
		  List<String> fields = Arrays.asList("upi", "checksum", "length", "sequence");
		  UniParcEntryMap entryMap = new UniParcEntryMap(entry, fields);
		  List<String> result = entryMap.getData();
		  assertEquals(fields.size(), result.size());
		  verify("UPI0000083A08", 0, result);
		  verify("AA7812161AF4EB5E", 1, result);
		  verify("30", 2, result);
		  verify("MVSWGRFICLVVVTMATLSLARPSFSLVED", 3, result);
	}
	
	@Test
	void testGetSequenceFeature() {
		UniParcEntry entry = create();
		  List<String> fields = Arrays.asList("upi", "HAMAP", "InterPro", "Pfam", "PROSITE");
		  UniParcEntryMap entryMap = new UniParcEntryMap(entry, fields);
		  List<String> result = entryMap.getData();
		  assertEquals(fields.size(), result.size());
		  verify("UPI0000083A08", 0, result);
		  verify("", 1, result);
		  verify("id1", 2, result);
		  verify("sigId2", 3, result);
		  verify("sigId2", 4, result);
	}
	
	
	  private void verify(String expected, int pos, List<String> result) {
	        assertEquals(expected, result.get(pos));
	    }
	private UniParcEntry create() {
		String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
		Sequence sequence = new SequenceBuilder(seq).build();
		List<UniParcDBCrossReference> xrefs = getXrefs();
		List<SequenceFeature> seqFeatures = getSeqFeatures() ;
		List<Taxonomy> taxonomies =getTaxonomies();
		UniParcEntry entry = new UniParcEntryBuilder().uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
				.databaseCrossReferences(xrefs).sequence(sequence)
				.sequenceFeatures(seqFeatures)
				.taxonomies(taxonomies).build();
		return entry;
	}
	private List<Taxonomy> getTaxonomies(){
		Taxonomy taxonomy = TaxonomyBuilder.newInstance().taxonId(9606).scientificName("Homo sapiens").build();
		Taxonomy taxonomy2 = TaxonomyBuilder.newInstance().taxonId(10090).scientificName("MOUSE").build();
		return Arrays.asList(taxonomy, taxonomy2);
	}
	private List<SequenceFeature> getSeqFeatures() {
		List<Location> locations = Arrays.asList(new Location(12, 23), new Location(45, 89));
		InterproGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
		SequenceFeature sf = new SequenceFeatureBuilder().interproGroup(domain).signatureDbType(SignatureDbType.PFAM)
				.signatureDbId("sigId2").locations(locations).build();
		SequenceFeature sf3 = new SequenceFeatureBuilder().from(sf).signatureDbType(SignatureDbType.PROSITE).build();
		return Arrays.asList(sf, sf3);
	}

	private List<UniParcDBCrossReference> getXrefs() {
		List<Property> properties = new ArrayList<>();
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
		UniParcDBCrossReference xref = new UniParcDBCrossReferenceBuilder().versionI(3)
				.databaseType(UniParcDatabaseType.SWISSPROT).id("P12345").version(7).active(true)
				.created(LocalDate.of(2017, 5, 17)).lastUpdated(LocalDate.of(2017, 2, 27)).properties(properties)
				.build();

		List<Property> properties2 = new ArrayList<>();
		properties2.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
		properties2.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));

		UniParcDBCrossReference xref2 = new UniParcDBCrossReferenceBuilder().versionI(1)
				.databaseType(UniParcDatabaseType.TREMBL).id("P52346").version(7).active(true)
				.created(LocalDate.of(2017, 2, 12)).lastUpdated(LocalDate.of(2017, 4, 23)).properties(properties2)
				.build();

		return Arrays.asList(xref, xref2);
	}
}
