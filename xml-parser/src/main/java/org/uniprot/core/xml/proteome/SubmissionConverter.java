package org.uniprot.core.xml.proteome;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.CitationType;

import com.google.common.base.Strings;

public class SubmissionConverter implements Converter<CitationType, Submission> {
    private final ObjectFactory xmlFactory;

    public SubmissionConverter() {
        this(new ObjectFactory());
    }

    public SubmissionConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Submission fromXml(CitationType xmlObj) {
        SubmissionBuilder builder = new SubmissionBuilder();
        ReferenceConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlObj.getDb()))
                .title(xmlObj.getTitle());
        return builder.build();
    }

    @Override
    public CitationType toXml(Submission uniObj) {
        CitationType xmlCitation = xmlFactory.createCitationType();
        xmlCitation.setType(org.uniprot.core.citation.CitationType.SUBMISSION.getDisplayName());
        ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlCitation, uniObj);
        xmlCitation.setDb(uniObj.getSubmissionDatabase().getName());

        if (!Strings.isNullOrEmpty(uniObj.getTitle())) {
            xmlCitation.setTitle(uniObj.getTitle());
        }
        return xmlCitation;
    }
}
