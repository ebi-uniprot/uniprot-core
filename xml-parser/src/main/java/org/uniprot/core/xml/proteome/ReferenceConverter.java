package org.uniprot.core.xml.proteome;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ReferenceType;

public class ReferenceConverter implements Converter<ReferenceType, Citation> {
    public static final String JOURNAL_ARTICLE = "journal article";
    public static final String SUBMISSION = "submission";
	private final JournalArticleConverter journalArticleConverter;
    private final SubmissionConverter submissionConverter;

    public ReferenceConverter() {
        this(new ObjectFactory());
    }

    public ReferenceConverter(ObjectFactory xmlFactory) {
        journalArticleConverter = new JournalArticleConverter(xmlFactory);
        submissionConverter = new SubmissionConverter(xmlFactory);
    }

    @Override
    public Citation fromXml(ReferenceType xmlObj) {
    	if(xmlObj.getCitation().getType().equalsIgnoreCase(JOURNAL_ARTICLE))
    		return journalArticleConverter.fromXml(xmlObj);
    	else if(xmlObj.getCitation().getType().equalsIgnoreCase(SUBMISSION))
        	return submissionConverter.fromXml(xmlObj);
        else return null;
    }

    @Override
    public ReferenceType toXml(Citation uniObj) {
        if (uniObj instanceof JournalArticle) {
            return journalArticleConverter.toXml((JournalArticle) uniObj);
        } else if (uniObj instanceof Submission) {
            return submissionConverter.toXml((Submission) uniObj);
        } else return null;
    }
}
