package uk.ac.ebi.uniprot.domain.proteome.builder;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.Proteome;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeType;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;

class ProteomeBuilderTest {

	@Test
	void testFrom() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		Proteome proteome = ProteomeBuilder.newInstance().proteomeId(proteomeId)
				.build();
		
		Proteome proteome2 = ProteomeBuilder.newInstance().from(proteome).build();
		assertEquals(proteome, proteome2);
	}

	@Test
	void testProteomeId() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		Proteome proteome = ProteomeBuilder.newInstance().proteomeId(proteomeId)
				.build();
		assertEquals(proteomeId, proteome.getId());
	}

	@Test
	void testName() {
		String name ="Human";
		Proteome proteome = ProteomeBuilder.newInstance().name(name)
				.build();
		assertEquals(name, proteome.getName());
	}

	@Test
	void testDescription() {
		String description ="about some proteome";
		Proteome proteome = ProteomeBuilder.newInstance().description(description)
				.build();
		assertEquals(description, proteome.getDescription());
	}

	@Test
	void testTaxonomy() {
		long taxonomy = 9606;
		Proteome proteome = ProteomeBuilder.newInstance().taxonomy(taxonomy)
				.build();
		assertEquals(taxonomy, proteome.getTaxonomy());
	}

	@Test
	void testModified() {
		LocalDate modified = LocalDate.of(2015, 11, 5);
		Proteome proteome = ProteomeBuilder.newInstance().modified(modified)
				.build();
		assertEquals(modified, proteome.getModified());
	}

	@Test
	void testProteomeType() {
		ProteomeType type = ProteomeType.NORMAL;
		Proteome proteome = ProteomeBuilder.newInstance().proteomeType(type)
				.build();
		assertEquals(type, proteome.getProteomeType());
		
		type = ProteomeType.REFERENCE;
		proteome = ProteomeBuilder.newInstance().proteomeType(type)
				.build();
		assertEquals(type, proteome.getProteomeType());
	}

	@Test
	void testRedundantTo() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		Proteome proteome = ProteomeBuilder.newInstance().redundantTo(proteomeId)
				.build();
		assertEquals(proteomeId, proteome.getRedundantTo());
	}

	@Test
	void testStrain() {
		String  strain ="some strains";
		Proteome proteome = ProteomeBuilder.newInstance().strain(strain)
				.build();
		assertEquals(strain, proteome.getStrain());
	}

	@Test
	void testIsolate() {
		String  isolate ="some isolate";
		Proteome proteome = ProteomeBuilder.newInstance().isolate(isolate)
				.build();
		assertEquals(isolate, proteome.getIsolate());
	}

	@Test
	void testDbXReferences() {
		List<DBCrossReference<ProteomeXReferenceType>> xrefs =new ArrayList<>();
		DBCrossReference<ProteomeXReferenceType> xref1 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ACCESSION)
				.id("ACA121")
				.build();
		DBCrossReference<ProteomeXReferenceType> xref2 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ANNOTATION)
				.id("ADFDA121")
				.build();
		xrefs.add(xref1);
		xrefs.add(xref2);
		Proteome proteome = ProteomeBuilder.newInstance().dbXReferences(xrefs)
		 .build();
		assertEquals(2, proteome.getDbXReferences().size());
		assertThat(proteome.getDbXReferences(), hasItem(xref2));
	}

	@Test
	void testAddDbXReferences() {

		
		DBCrossReference<ProteomeXReferenceType> xref1 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ACCESSION)
				.id("ACA121")
				.build();
		DBCrossReference<ProteomeXReferenceType> xref2 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ANNOTATION)
				.id("ADFDA121")
				.build();
		Proteome proteome = ProteomeBuilder.newInstance().addDbXReferences(xref1)
		 .addDbXReferences(xref2)
		 .build();
		assertEquals(2, proteome.getDbXReferences().size());
		assertThat(proteome.getDbXReferences(), hasItem(xref1));
	}

	@Test
	void testComponents() {
		List<Component> components = new ArrayList<>();
		Component component1 =
		ComponentBuilder.newInstance()
		.name("someName1").description("some description")
		.proteinCount(102)
		.build();
		
		Component component2 =
				ComponentBuilder.newInstance()
				.name("someName2").description("some description 2")
				.proteinCount(102)
				.build();
		
		components.add(component1);
		components.add(component2);
		Proteome proteome = ProteomeBuilder.newInstance().components(components)
				 .build();
				assertEquals(2, proteome.getComponents().size());
				assertThat(proteome.getComponents(), hasItem(component1));
	}

	@Test
	void testAddComponent() {
		fail("Not yet implemented");
	}

	@Test
	void testReferences() {
		fail("Not yet implemented");
	}

	@Test
	void testAddReferenceCitation() {
		fail("Not yet implemented");
	}

	@Test
	void testRedundantProteome() {
		fail("Not yet implemented");
	}

	@Test
	void testAddReferenceRedundantProteome() {
		fail("Not yet implemented");
	}

	@Test
	void testPanproteome() {
		fail("Not yet implemented");
	}

	@Test
	void testAnnotationScore() {
		fail("Not yet implemented");
	}

	@Test
	void testSuperkingdom() {
		fail("Not yet implemented");
	}

	@Test
	void testProteinCount() {
		fail("Not yet implemented");
	}

	@Test
	void testGeneCount() {
		fail("Not yet implemented");
	}

}
