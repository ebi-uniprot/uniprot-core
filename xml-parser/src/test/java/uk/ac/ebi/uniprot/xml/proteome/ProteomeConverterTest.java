package uk.ac.ebi.uniprot.xml.proteome;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.proteome.*;
import uk.ac.ebi.uniprot.domain.proteome.builder.ComponentBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeEntryBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeIdBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.TaxonomyBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProteomeConverterTest {

	@Test
	void test() {	
		ProteomeConverter converter = new ProteomeConverter();
		ProteomeEntry proteome =create();
		uk.ac.ebi.uniprot.xml.jaxb.proteome.Proteome xml = converter.toXml(proteome);
		ProteomeEntry converted = converter.fromXml(xml);
		assertEquals(proteome, converted);
	}
	private ProteomeEntry create() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		String description ="about some proteome";
		Taxonomy taxonomy = TaxonomyBuilder.newInstance().taxonId(9606).scientificName("Homo sapiens").build();
		LocalDate modified = LocalDate.of(2015, 11, 5);
	//	String reId = "UP000005641";
	//	ProteomeId redId = new ProteomeIdBuilder (reId).build();
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
		.type(uk.ac.ebi.uniprot.domain.proteome.ComponentType.UNPLACED)				
		.build();
		
		Component component2 =
				ComponentBuilder.newInstance()
				.name("someName2").description("some description 2")
				.type(uk.ac.ebi.uniprot.domain.proteome.ComponentType.SEGMENTED_GENOME)			
				.build();
		
		components.add(component1);
		components.add(component2);
		List<Citation> citations = new ArrayList<>();
		citations.add(createJournal());
		citations.add(createSubmission());
		ProteomeEntryBuilder builder = ProteomeEntryBuilder.newInstance().proteomeId(proteomeId)
				.description(description)
				.taxonomy(taxonomy)
				.modified(modified)
				.proteomeType(ProteomeType.NORMAL)
			//	.redundantTo(redId)
				.dbXReferences(xrefs)
				.components(components)
				.superkingdom(Superkingdom.EUKARYOTA)
				.references(citations)
				.annotationScore(15);
		
		return builder.build();
	}
	private JournalArticle createJournal() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223").volume("2").title(title)
                .publicationDate(date)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .authoringGroups(Arrays.asList("The C. elegans sequencing consortium"))
                .citationXrefs(Arrays.asList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
                                             new DBCrossReferenceImpl<>(CitationXrefType.DOI,
                                                                        "https://doi.org/10.1126/science.282.5396.2012")));
        JournalArticle citation = builder.build();
        return citation;
    }
	 private Submission createSubmission() {
	        SubmissionBuilder builder = new SubmissionBuilder();
	        String date = "JAN-2018";
	        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
	                .addAuthor("Sulson J.E.")
	                .addAuthor("JWaterston R.")
	                .title("Another title")
	                .publicationDate(date);
	        return builder.build();
	    }
}
