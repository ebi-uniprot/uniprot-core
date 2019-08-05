package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public enum CitationConverterFactory {
	INSTANCE;

	@SuppressWarnings("unchecked")
	public <T extends Citation>  Converter<CitationType, T> getConverter(ObjectFactory xmlUniprotFactory,
			org.uniprot.core.citation.CitationType type){
		switch (type) {
		case JOURNAL_ARTICLE:
			return (Converter<CitationType, T>) new JournalArticleConverter(xmlUniprotFactory);
		case BOOK:
			return (Converter<CitationType, T>) new BookConverter(xmlUniprotFactory);
		case ELECTRONIC_ARTICLE:
			return (Converter<CitationType, T>) new ElectronicArticleConverter(xmlUniprotFactory);
		case PATENT:
			return (Converter<CitationType, T>) new PatentConverter(xmlUniprotFactory);
		case SUBMISSION:
			return (Converter<CitationType, T>) new SubmissionConverter(xmlUniprotFactory);
		case THESIS:
			return (Converter<CitationType, T>) new ThesisConverter(xmlUniprotFactory);
		case UNPUBLISHED:
			return (Converter<CitationType, T>) new UnpublishedConverter(xmlUniprotFactory);
			default:
				return null;
		}

	}
}
