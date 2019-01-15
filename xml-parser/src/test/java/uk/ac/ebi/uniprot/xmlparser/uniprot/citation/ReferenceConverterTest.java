package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtReferenceFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReferenceType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ReferenceConverterTest {

	@Test
	void testSubmission() {
		Submission submission = createSubmission();

		List<String> referencePositions = Arrays.asList("Some position");
		List<Evidence> evidences = createEvidences();
		List<ReferenceComment> refComments = new ArrayList<>();
		refComments.add(
				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
		refComments.add(
				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
		UniProtReference uniReference = UniProtReferenceFactory.INSTANCE.createUniProtReference(submission,
				referencePositions, refComments, Collections.emptyList());
		ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
		ReferenceType xmlReference = converter.toXml(uniReference);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
		UniProtReference converted = converter.fromXml(xmlReference);
		assertEquals(uniReference, converted);

	}

	@Test
	void testJournalArticle() {
		JournalArticle citation = createJournalArticle();

		List<String> referencePositions = Arrays.asList("Some position");
		List<Evidence> evidences = createEvidences();
		List<ReferenceComment> refComments = new ArrayList<>();
		refComments.add(
				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
		refComments.add(
				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
		UniProtReference uniReference = UniProtReferenceFactory.INSTANCE.createUniProtReference(citation,
				referencePositions, refComments, evidences);
		ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
		ReferenceType xmlReference = converter.toXml(uniReference);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
		UniProtReference converted = converter.fromXml(xmlReference);
		assertEquals(uniReference, converted);

	}

	@Test
	void testBook() {
		Book citation = createBook();

		List<String> referencePositions = Arrays.asList("Some position");
		List<Evidence> evidences = createEvidences();
		List<ReferenceComment> refComments = new ArrayList<>();
//		refComments.add(
//				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
//		refComments.add(
//				UniProtReferenceFactory.INSTANCE.createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
		UniProtReference uniReference = UniProtReferenceFactory.INSTANCE.createUniProtReference(citation,
				referencePositions, refComments, evidences);
		ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
		ReferenceType xmlReference = converter.toXml(uniReference);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
		UniProtReference converted = converter.fromXml(xmlReference);
		assertEquals(uniReference, converted);

	}

	private Submission createSubmission() {
		SubmissionBuilder builder = SubmissionBuilder.newInstance();
		String title = "Protein research";
		String date = "JAN-2018";
		builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ).title(title)
				.authors(Arrays.asList(AbstractCitationBuilder.createAuthor("Sulson J.E."),
						AbstractCitationBuilder.createAuthor("JWaterston R.")))
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		return builder.build();
	}

	private JournalArticle createJournalArticle() {
		JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
		String title = "Some article title";
		String date = "2008";
		String journalName = "Nature";
		builder.journalName(journalName).firstPage("213").lastPage("223").volume("2").title(title)
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date))
				.authors(Arrays.asList(AbstractCitationBuilder.createAuthor("Sulson J.E."),
						AbstractCitationBuilder.createAuthor("JWaterston R.")))
				.authoringGroups(Arrays.asList("The C. elegans sequencing consortium"))
				.citationXrefs(Arrays.asList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
						new DBCrossReferenceImpl<>(CitationXrefType.DOI,
								"https://doi.org/10.1126/science.282.5396.2012")));
		JournalArticle citation = builder.build();
		return citation;
	}

	private Book createBook() {
		BookBuilder builder = BookBuilder.newInstance();
		String bookName = "Some book name";
		String title = "Some article title";
		String date = "2009";
		builder.bookName(bookName)
				.editors(Arrays.asList(AbstractCitationBuilder.createAuthor("David"),
						AbstractCitationBuilder.createAuthor("Charlie")))
				.firstPage("234").lastPage("324C").volume("3").publisher("London Press").address("London").title(title)
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		;
		Book book = builder.build();
		return book;
	}

	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}
}
