package uk.ac.ebi.uniprot.xml.proteome;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ReferenceType;

public class ReferenceConverter implements Converter<ReferenceType, Citation> {
	private final JournalArticleConverter journalArticleConverter;
	private final SubmissionConverter submissionConverter;
	public ReferenceConverter() {
		this(new ObjectFactory());
	}

	public ReferenceConverter(ObjectFactory xmlFactory) {
		journalArticleConverter =new JournalArticleConverter(xmlFactory);
		submissionConverter = new SubmissionConverter(xmlFactory);
	}
	
	@Override
	public Citation fromXml(ReferenceType xmlObj) {
		if(xmlObj.getJournal() !=null)
			return journalArticleConverter.fromXml(xmlObj);
		else if(xmlObj.getSubmission() !=null)
			return submissionConverter.fromXml(xmlObj);
		else
			return null;
	}

	@Override
	public ReferenceType toXml(Citation uniObj) {
		if(uniObj instanceof JournalArticle) {
			return journalArticleConverter.toXml((JournalArticle) uniObj);
		}else if(uniObj instanceof Submission) { 
			return submissionConverter.toXml((Submission) uniObj);
		}else
			return null;
	}
}
