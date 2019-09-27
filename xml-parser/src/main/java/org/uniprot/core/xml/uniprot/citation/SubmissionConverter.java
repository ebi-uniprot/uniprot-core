package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class SubmissionConverter implements Converter<CitationType, Submission> {

    private final ObjectFactory xmlUniprotFactory;

    public SubmissionConverter() {
        this(new ObjectFactory());
    }

    public SubmissionConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Submission fromXml(CitationType xmlObj) {
        SubmissionBuilder builder = new SubmissionBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlObj.getDb()));
        return builder.build();
    }

    @Override
    public CitationType toXml(Submission uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
        xmlCitation.setType(uniObj.getCitationType().getValue());
        xmlCitation.setDb(uniObj.getSubmissionDatabase().getName());
        return xmlCitation;
    }
}
