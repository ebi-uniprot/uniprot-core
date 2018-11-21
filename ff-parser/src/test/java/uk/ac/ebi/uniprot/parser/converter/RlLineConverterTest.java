package uk.ac.ebi.uniprot.parser.converter;

import org.junit.Test;

import static junit.framework.TestCase.*;

import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.CitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;

import uk.ac.ebi.uniprot.parser.impl.rl.RlLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject;

public class RlLineConverterTest {
	private final RlLineConverter converter = new RlLineConverter();
	@Test
	public void testJournalArticle(){
		// "RL   J. Mol. Biol. 168:321-331(1983).
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.JournalArticle ja = new RlLineObject.JournalArticle();
		ja.first_page ="321";
		ja.last_page ="331";
		ja.volume ="168";
		ja.year =1983;
		ja.journal = "J. Mol. Biol.";
		rlObject.reference = ja;
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof JournalArticleBuilder);
		JournalArticle journal =((JournalArticleBuilder) builder).build();

		assertEquals(CitationType.JOURNAL_ARTICLE, journal.getCitationType());

		assertEquals("321", journal.getFirstPage());
		assertEquals("331", journal.getLastPage());
		assertEquals("168", journal.getVolume());
		assertEquals("1983", journal.getPublicationDate().getValue());
		assertEquals("J. Mol. Biol.", journal.getJournalName());
		
	}
	@Test
	public void testSubmission(){
		// "RL   Submitted (OCT-1995) to the EMBL/GenBank/DDBJ databases.
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Submission subm = new RlLineObject.Submission();
		subm.month ="OCT";
		subm.year =1995;
		subm.db =RlLineObject.SubmissionDB.EMBL;
		rlObject.reference = subm;
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof SubmissionBuilder);
		Submission submission =((SubmissionBuilder) builder).build();

		assertEquals(CitationType.SUBMISSION, submission.getCitationType());

		assertEquals("OCT-1995", submission.getPublicationDate().getValue());
		assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, submission.getSubmissionDatabase());
	}
	@Test
	public void testThesis(){
		// "RL   Thesis (1977), University of Geneva, Switzerland.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Thesis th = new RlLineObject.Thesis();
		th.country ="Switzerland";
		th.institute ="University of Geneva";
		th.year =1977;
		rlObject.reference = th;
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof ThesisBuilder);
		Thesis thesis =((ThesisBuilder) builder).build();

		assertEquals(CitationType.THESIS, thesis.getCitationType());

		assertEquals("Switzerland", thesis.getAddress());
		assertEquals("1977", thesis.getPublicationDate().getValue());
		assertEquals("University of Geneva", thesis.getInstitute());
	}
	
	@Test
	public void testPatent(){
		//"RL   Patent number WO9010703, 20-SEP-1990.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Patent th = new RlLineObject.Patent();
		th.day =20;
		th.month ="SEP";
		th.year =1990;
		th.patentNumber ="WO9010703";
		rlObject.reference = th;
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof PatentBuilder);
		Patent patent =((PatentBuilder) builder).build();

		assertEquals(CitationType.PATENT, patent.getCitationType());

		assertEquals("WO9010703", patent.getPatentNumber());
		assertEquals("20-SEP-1990", patent.getPublicationDate().getValue());
		
	}

	
	@Test
	public void testBook(){
		//RL   (In) Boyer P.D. (eds.);
        //RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York (1975).
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Book th = new RlLineObject.Book();
		th.editors.add("Boyer P.D.");
		th.title = "The enzymes (3rd ed.)";
		th.page_start ="397";
		th.page_end ="547";
		th.place ="New York";
		th.press = "Academic Press";
		th.volume ="11";
		th.year =1975;
		rlObject.reference = th;
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof BookBuilder);
		Book book =((BookBuilder) builder).build();

		assertEquals(CitationType.BOOK, book.getCitationType());
		assertEquals(1, book.getEditors().size());
		assertEquals("Boyer P.D.", book.getEditors().get(0).getValue());
		assertEquals("The enzymes (3rd ed.)", book.getBookName());
		assertEquals("397", book.getFirstPage());
		assertEquals("547", book.getLastPage());
		assertEquals("1975", book.getPublicationDate().getValue());
		assertEquals("11", book.getVolume());
	}
	@Test
	public void testElectronicArticle(){
		// "RL   (er) Plant Gene Register PGR98-023.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.EPub th = new RlLineObject.EPub();
		th.title ="Plant Gene Register PGR98-023";
		
		rlObject.reference = th;

		
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof ElectronicArticleBuilder);
		ElectronicArticle ea =((ElectronicArticleBuilder) builder).build();
		assertEquals(CitationType.ELECTRONIC_ARTICLE, ea.getCitationType());
		assertEquals("Plant Gene Register", ea.getJournalName());
		assertEquals("PGR98-023", ea.getLocator().getValue());
		
	}
	@Test
	public void testUnpublished(){
		// "RL   Unpublished observations (OCT-1978).\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Unpublished th = new RlLineObject.Unpublished();
		th.month ="OCT";
		th.year =1978;
		
		rlObject.reference = th;

	
		CitationBuilder builder = converter.convert(rlObject);
		assertTrue(builder instanceof UnpublishedBuilder);
		Unpublished ea =((UnpublishedBuilder) builder).build();
		
		assertEquals(CitationType.UNPUBLISHED, ea.getCitationType());
		assertEquals("OCT-1978", ea.getPublicationDate().getValue());
	}
}
