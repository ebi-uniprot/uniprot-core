package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.impl.ThesisBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class ThesisConverter implements Converter<CitationType, Thesis> {

    private final ObjectFactory xmlUniprotFactory;

    public ThesisConverter() {
        this(new ObjectFactory());
    }

    public ThesisConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Thesis fromXml(CitationType xmlObj) {
        ThesisBuilder builder = new ThesisBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        builder.institute(xmlObj.getInstitute());
        builder.address(xmlObj.getCountry());

        return builder.build();
    }

    @Override
    public CitationType toXml(Thesis uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
        xmlCitation.setType(uniObj.getCitationType().getName());
        xmlCitation.setInstitute(uniObj.getInstitute());
        xmlCitation.setCountry(uniObj.getAddress());
        return xmlCitation;
    }
}
