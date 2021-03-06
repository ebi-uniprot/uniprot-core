package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import com.google.common.base.Strings;

public class ElectronicArticleConverter implements Converter<CitationType, ElectronicArticle> {

    private final ObjectFactory xmlUniprotFactory;

    public ElectronicArticleConverter() {
        this(new ObjectFactory());
    }

    public ElectronicArticleConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ElectronicArticle fromXml(CitationType xmlObj) {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        if (xmlObj.getName() != null) builder.journalName(xmlObj.getName());
        builder.locator(xmlObj.getLocator());
        return builder.build();
    }

    @Override
    public CitationType toXml(ElectronicArticle uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);

        xmlCitation.setType(uniObj.getCitationType().getName());
        if ((uniObj.getJournal() != null) && !Strings.isNullOrEmpty(uniObj.getJournal().getName()))
            xmlCitation.setName(uniObj.getJournal().getName());
        xmlCitation.setLocator(uniObj.getLocator().getValue());
        return xmlCitation;
    }
}
