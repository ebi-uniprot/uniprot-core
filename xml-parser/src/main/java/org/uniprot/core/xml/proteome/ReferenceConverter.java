package org.uniprot.core.xml.proteome;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.CitationType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ReferenceType;

public class ReferenceConverter implements Converter<ReferenceType, Citation> {
    private final JournalArticleConverter journalArticleConverter;
    private final SubmissionConverter submissionConverter;
    private final ObjectFactory xmlFactory;

    public ReferenceConverter() {
        this(new ObjectFactory());
    }

    public ReferenceConverter(ObjectFactory xmlFactory) {
        journalArticleConverter = new JournalArticleConverter(xmlFactory);
        submissionConverter = new SubmissionConverter(xmlFactory);
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Citation fromXml(ReferenceType xmlObj) {
        Citation result = null;
        if(xmlObj.getCitation() != null) {
            org.uniprot.core.citation.CitationType type =
                    org.uniprot.core.citation.CitationType.typeOf(xmlObj.getCitation().getType());
            switch (type) {
                case JOURNAL_ARTICLE:
                    result = journalArticleConverter.fromXml(xmlObj.getCitation());
                    break;
                case SUBMISSION:
                    result = submissionConverter.fromXml(xmlObj.getCitation());
                    break;
                default:
                    result = null;
            }
        }
        return result;
    }

    @Override
    public ReferenceType toXml(Citation uniObj) {
        CitationType citationType;
        if (uniObj instanceof JournalArticle) {
            citationType = journalArticleConverter.toXml((JournalArticle) uniObj);
        } else if (uniObj instanceof Submission) {
            citationType = submissionConverter.toXml((Submission) uniObj);
        } else {
            citationType = null;
        }
        if(Utils.notNull(citationType)) {
            ReferenceType referenceType = xmlFactory.createReferenceType();
            referenceType.setCitation(citationType);
            return referenceType;
        } else {
            return null;
        }
    }
}
