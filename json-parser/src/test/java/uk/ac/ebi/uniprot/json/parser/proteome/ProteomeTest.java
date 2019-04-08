package uk.ac.ebi.uniprot.json.parser.proteome;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
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
import uk.ac.ebi.uniprot.domain.proteome.builder.ComponentBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeIdBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.RedundantProteomeBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

public class ProteomeTest {
	@Test
	void testComponent() {
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
		Component component = ComponentBuilder.newInstance()
				.name("someName").description("some description")
				.proteinCount(102)
				.dbXReferences(xrefs)
				.build();
		 ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getObjectMapper(), component);
	}
	
	@Test
	void testProteomeId() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		 ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getObjectMapper(), proteomeId);
	}
	@Test
	void testRedundantProteome() {
		String id = "UP000004340";
		RedundantProteome rproteome =
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (id).build())
				.similarity(0.98)
				.build();
		 ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getObjectMapper(), rproteome);	
	}
	@Test
	void testProteome() {
		String id = "UP000005640";
		String description ="about some proteome";
		LocalDate modified = LocalDate.of(2015, 11, 5);
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		String redundantId = "UP000005642";
		ProteomeId  redId = new ProteomeIdBuilder (redundantId).build();
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
		Proteome proteome = ProteomeBuilder.newInstance().proteomeId(proteomeId)
				.name("Human")
				.description(description)
				.taxonomy(9606)
				.modified(modified)
				.proteomeType(ProteomeType.REDUNDANT)
				.redundantTo(redId)
				.strain("some Strain")
				.dbXReferences(xrefs)
				.references(getCitations())
				.superkingdom(Superkingdom.EUKARYOTA)
				.build();
		
		 ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getObjectMapper(), proteome);	
	}
	
	@Test
	void testReferenceProteome() {
		String id = "UP000005640";
		String description ="about some proteome";
		LocalDate modified = LocalDate.of(2015, 11, 5);
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();

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
		List<RedundantProteome> redundantProteomes = new ArrayList<>();
		String rid = "UP000004340";
		RedundantProteome rproteome1 =
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (rid).build())
				.similarity(0.98)
				.build();
		String rid2 = "UP000004343";
		RedundantProteome rproteome2=
				RedundantProteomeBuilder.newInstance()
				.proteomeId(new ProteomeIdBuilder (rid2).build())
				.similarity(0.88)
				.build();
		redundantProteomes.add(rproteome1);
		redundantProteomes.add(rproteome2);
		
		
		Proteome proteome = ProteomeBuilder.newInstance().proteomeId(proteomeId)
				.name("Human")
				.description(description)
				.taxonomy(9606)
				.modified(modified)
				.proteomeType(ProteomeType.REFERENCE)
				.strain("some Strain")
				.dbXReferences(xrefs)
				.references(getCitations())
				.superkingdom(Superkingdom.EUKARYOTA)
			//	.components(components)
				.redundantProteomes(redundantProteomes)
				.build();
		
		 ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getObjectMapper(), proteome);	
	}
	

	private List<Citation> getCitations(){

	        JournalArticle citation1 =getJournalArticle();
	        Submission citation2 = getSubmission();
		 
	        List<Citation> citations = new ArrayList<>();
	        citations.add(citation1);
	        citations.add(citation2);
	        return citations;
	}
	private JournalArticle getJournalArticle(){
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new JournalArticleBuilder()
                .journalName("journal name")
                .firstPage("first page")
                .lastPage("last page")
                .volume("volume value")
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
	private Submission getSubmission(){
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new SubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.PIR)
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }

	
}
