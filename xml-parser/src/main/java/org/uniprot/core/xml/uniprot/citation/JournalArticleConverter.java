package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import com.google.common.base.Strings;

public class JournalArticleConverter implements Converter<CitationType, JournalArticle> {

    private final ObjectFactory xmlUniprotFactory;
    private final PageConverter pageConverter = new PageConverter();

    public JournalArticleConverter() {
        this(new ObjectFactory());
    }

    public JournalArticleConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public JournalArticle fromXml(CitationType xmlObj) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
        builder.journalName(xmlObj.getName());
        builder.firstPage(pageConverter.fromXml(xmlObj.getFirst()));
        builder.lastPage(pageConverter.fromXml(xmlObj.getLast()));
        builder.volume(xmlObj.getVolume());

        return builder.build();
    }

    @Override
    public CitationType toXml(JournalArticle uniObj) {
        CitationType xmlCitation = xmlUniprotFactory.createCitationType();
        CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
        xmlCitation.setType(uniObj.getCitationType().getName());
        xmlCitation.setName(uniObj.getJournal().getName());
        if (notBlankNotZero(uniObj.getFirstPage())) {
            xmlCitation.setFirst(pageConverter.toXml(uniObj.getFirstPage()));
        }
        if (notBlankNotZero(uniObj.getLastPage())) {
            xmlCitation.setLast(pageConverter.toXml(uniObj.getLastPage()));
        }
        if (notBlankNotZero(uniObj.getVolume())) {
            xmlCitation.setVolume(uniObj.getVolume());
        }

        return xmlCitation;
    }

    private boolean notBlankNotZero(String value) {
        return !Strings.isNullOrEmpty(value) && !value.equals("0");
    }
}
