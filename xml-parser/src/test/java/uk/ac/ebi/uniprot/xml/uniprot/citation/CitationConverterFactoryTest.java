package uk.ac.ebi.uniprot.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.citation.BookConverter;
import uk.ac.ebi.uniprot.xml.uniprot.citation.CitationConverterFactory;
import uk.ac.ebi.uniprot.xml.uniprot.citation.JournalArticleConverter;
import uk.ac.ebi.uniprot.xml.uniprot.citation.SubmissionConverter;

class CitationConverterFactoryTest {

	@Test
	void test() {
		ObjectFactory oFactory =	new ObjectFactory();
		uk.ac.ebi.uniprot.domain.citation.CitationType type =
				uk.ac.ebi.uniprot.domain.citation.CitationType.BOOK;		
		Converter<CitationType, ? extends Citation>  converter
		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof BookConverter);
		
		
		type =
				uk.ac.ebi.uniprot.domain.citation.CitationType.JOURNAL_ARTICLE;		
		converter		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof JournalArticleConverter);
		
		type =
				uk.ac.ebi.uniprot.domain.citation.CitationType.SUBMISSION;		
		converter		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof SubmissionConverter);
	}

}
