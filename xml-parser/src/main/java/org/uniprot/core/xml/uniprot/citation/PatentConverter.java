package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.impl.PatentBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class PatentConverter implements Converter<CitationType, Patent> {

    private final ObjectFactory xmlUniprotFactory;

    public PatentConverter() {
        this(new ObjectFactory());
    }

    public PatentConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Patent fromXml(CitationType xmlObj) {
        PatentBuilder builder = new PatentBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        builder.patentNumber(xmlObj.getNumber());
        return builder.build();
    }

    @Override
    public CitationType toXml(Patent uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
        xmlCitation.setType(uniObj.getCitationType().getName());
        xmlCitation.setNumber(uniObj.getPatentNumber());
        return xmlCitation;
    }
}
