package uk.ac.ebi.uniprot.domain.proteome.builder;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.Proteome;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeType;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.Superkingdom;

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
				
				Proteome proteome = ProteomeBuilder.newInstance().addComponent(component1)
						.addComponent(component2)
						 .build();
						assertEquals(2, proteome.getComponents().size());
						assertThat(proteome.getComponents(), hasItem(component2));
	}

	@Test
	void testReferences() {
		 JournalArticleBuilder builder = new JournalArticleBuilder();
		 this.buildCitationParameters(builder);
	        JournalArticle citation1 = builder.build();
	        
	        SubmissionBuilder builder2 = new SubmissionBuilder();
	        buildCitationParameters(builder2);

	        builder2.submittedToDatabase(SubmissionDatabase.PDB);
	        Submission citation2 = builder2.build();
		 
	        List<Citation> citations = new ArrayList<>();
	        citations.add(citation1);
	        citations.add(citation2);
	        Proteome proteome = ProteomeBuilder.newInstance().references(citations)
					 .build();
					assertEquals(2, proteome.getReferences().size());
					assertThat(proteome.getReferences(), hasItem(citation1));
	        
	}

	@Test
	void testAddReferenceCitation() {
		 JournalArticleBuilder builder = new JournalArticleBuilder();
		 this.buildCitationParameters(builder);
	        JournalArticle citation1 = builder.build();
	        
	        SubmissionBuilder builder2 = new SubmissionBuilder();
	        buildCitationParameters(builder2);

	        builder2.submittedToDatabase(SubmissionDatabase.PDB);
	        Submission citation2 = builder2.build();


	        Proteome proteome = ProteomeBuilder.newInstance().addReference(citation1)
	        		.addReference(citation2)
					 .build();
					assertEquals(2, proteome.getReferences().size());
					assertThat(proteome.getReferences(), hasItem(citation2));
	}

	  void buildCitationParameters(AbstractCitationBuilder<?, ?> builder) {
		     final String TITLE = "Some title";
		    final String PUBLICATION_DATE = "2015-MAY";
		     final List<String> GROUPS = asList("T1", "T2");
		     final List<String> AUTHORS = asList("Tom", "John");
	        builder
	                .title(TITLE)
	                .publicationDate(PUBLICATION_DATE)
	                .authoringGroups(GROUPS)
	                .authors(AUTHORS)
	                .citationXrefs(asList(new DBCrossReferenceBuilder<CitationXrefType>()
	                                              .databaseType(CitationXrefType.PUBMED)
	                                              .id("id1")
	                                              .build(),
	                                      new DBCrossReferenceBuilder<CitationXrefType>()
	                                              .databaseType(CitationXrefType.AGRICOLA)
	                                              .id("id2")
	                                              .build()))
	                .build();
	    }
	
	@Test
	void testRedundantProteomes() {
		List<RedundantProteome> redundantProteomes = new ArrayList<>();
		String id = "UP000004340";
		RedundantProteome rproteome1 =
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (id).build())
				.similarity(0.98)
				.build();
		String id2 = "UP000004343";
		RedundantProteome rproteome2=
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (id2).build())
				.similarity(0.88)
				.build();
		redundantProteomes.add(rproteome1);
		redundantProteomes.add(rproteome2);
		
		Proteome proteome = ProteomeBuilder.newInstance().redundantProteomes(redundantProteomes)
				 .build();
				assertEquals(2, proteome.getRedudantProteomes().size());
				assertThat(proteome.getRedudantProteomes(), hasItem(rproteome1));
	}

	@Test
	void testAddRedundantProteome() {
		String id = "UP000004340";
		RedundantProteome rproteome1 =
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (id).build())
				.similarity(0.98)
				.build();
		String id2 = "UP000004343";
		RedundantProteome rproteome2=
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (id2).build())
				.similarity(0.88)
				.build();

		Proteome proteome = ProteomeBuilder.newInstance()
				.addRedundantProteome(rproteome1)
				.addRedundantProteome(rproteome2)
				 .build();
				assertEquals(2, proteome.getRedudantProteomes().size());
				assertThat(proteome.getRedudantProteomes(), hasItem(rproteome2));
	}

	@Test
	void testPanproteome() {
		String id = "UP000005644";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		Proteome proteome = ProteomeBuilder.newInstance().panproteome(proteomeId)
				.build();
		assertEquals(proteomeId, proteome.getPanproteome());
	}

	@Test
	void testAnnotationScore() {
		Proteome proteome = ProteomeBuilder.newInstance().annotationScore(20)
				.build();
		assertEquals(20, proteome.getAnnotationScore());
	}

	@Test
	void testSuperkingdom() {
		Superkingdom superkingdom = Superkingdom.EUKARYOTA;
		Proteome proteome = ProteomeBuilder.newInstance().superkingdom(superkingdom)
				.build();
		assertEquals(superkingdom, proteome.getSuperkingdom());
	}

	@Test
	void testProteinCount() {
		Proteome proteome = ProteomeBuilder.newInstance().proteinCount(905)
				.build();
		assertEquals(905, proteome.getProteinCount());
	}

	@Test
	void testGeneCount() {
		Proteome proteome = ProteomeBuilder.newInstance().geneCount(203)
				.build();
		assertEquals(203, proteome.getGeneCount());
	}

}
