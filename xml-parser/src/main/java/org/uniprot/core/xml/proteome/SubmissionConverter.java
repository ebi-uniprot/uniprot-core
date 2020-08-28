package org.uniprot.core.xml.proteome;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.CitationType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ReferenceType;

import com.google.common.base.Strings;

public class SubmissionConverter implements Converter<ReferenceType, Submission> {
    private final ObjectFactory xmlFactory;

    public SubmissionConverter() {
        this(new ObjectFactory());
    }

    public SubmissionConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Submission fromXml(ReferenceType xmlObj) {
    	  CitationType xmlSubmission = xmlObj.getCitation();
        SubmissionBuilder builder = new SubmissionBuilder();
        ReferenceConverterHelper.updateFromXmlCitaiton(xmlSubmission, builder);
      
        builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlSubmission.getDb()))
                .title(xmlSubmission.getTitle());
        return builder.build();
    }

    @Override
    public ReferenceType toXml(Submission uniObj) {
        ReferenceType xmlCitation = xmlFactory.createReferenceType();     
        CitationType xmlSubmission = xmlFactory.createCitationType();
        xmlSubmission.setType(ReferenceConverter.SUBMISSION);
        ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlSubmission, uniObj);
        xmlSubmission.setDb(uniObj.getSubmissionDatabase().getName());

        if (!Strings.isNullOrEmpty(uniObj.getTitle())) {
            xmlSubmission.setTitle(uniObj.getTitle());
        }
        xmlCitation.setCitation(xmlSubmission);
        return xmlCitation;
    }
}
