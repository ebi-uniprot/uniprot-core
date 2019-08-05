package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.citation.BookConverter;
import org.uniprot.core.xml.uniprot.citation.CitationConverterFactory;
import org.uniprot.core.xml.uniprot.citation.JournalArticleConverter;
import org.uniprot.core.xml.uniprot.citation.SubmissionConverter;

class CitationConverterFactoryTest {

	@Test
	void test() {
		ObjectFactory oFactory =	new ObjectFactory();
		org.uniprot.core.citation.CitationType type =
				org.uniprot.core.citation.CitationType.BOOK;		
		Converter<CitationType, ? extends Citation>  converter
		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof BookConverter);
		
		
		type =
				org.uniprot.core.citation.CitationType.JOURNAL_ARTICLE;		
		converter		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof JournalArticleConverter);
		
		type =
				org.uniprot.core.citation.CitationType.SUBMISSION;		
		converter		
		= CitationConverterFactory.INSTANCE.getConverter(oFactory, type);
		assertTrue( converter instanceof SubmissionConverter);
	}

}
