package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.impl.UnpublishedBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class UnpublishedConverter implements Converter<CitationType, Unpublished> {

    private final ObjectFactory xmlUniprotFactory;

    public UnpublishedConverter() {
        this(new ObjectFactory());
    }

    public UnpublishedConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Unpublished fromXml(CitationType xmlObj) {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        return builder.build();
    }

    @Override
    public CitationType toXml(Unpublished uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
        xmlCitation.setType(uniObj.getCitationType().getName());
        return xmlCitation;
    }
}
