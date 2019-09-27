package org.uniprot.core.xml.proteome;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ReferenceType;
import org.uniprot.core.xml.jaxb.proteome.SubmissionType;

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
        SubmissionBuilder builder = new SubmissionBuilder();
        ReferenceConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        SubmissionType xmlSubmission = xmlObj.getSubmission();
        builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlSubmission.getDb()))
                .title(xmlSubmission.getTitle());
        return builder.build();
    }

    @Override
    public ReferenceType toXml(Submission uniObj) {
        ReferenceType xmlCitation = xmlFactory.createReferenceType();
        ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlCitation, uniObj);
        SubmissionType xmlSubmission = xmlFactory.createSubmissionType();
        xmlSubmission.setDb(uniObj.getSubmissionDatabase().getName());

        if (!Strings.isNullOrEmpty(uniObj.getTitle())) {
            xmlSubmission.setTitle(uniObj.getTitle());
        }
        xmlCitation.setSubmission(xmlSubmission);
        return xmlCitation;
    }
}
