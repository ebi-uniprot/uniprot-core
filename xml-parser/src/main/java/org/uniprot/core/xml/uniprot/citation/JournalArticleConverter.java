package org.uniprot.core.xml.uniprot.citation;

import com.google.common.base.Strings;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

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
        if (!Strings.isNullOrEmpty(uniObj.getFirstPage()) &&
                !uniObj.getFirstPage().equals("0")){
            xmlCitation.setFirst(pageConverter.toXml(uniObj.getFirstPage()));
        }
        if (!Strings.isNullOrEmpty(uniObj.getLastPage()) &&
                !uniObj.getLastPage().equals("0")){
            xmlCitation.setLast(pageConverter.toXml(uniObj.getLastPage()));
        }
        if (!Strings.isNullOrEmpty(uniObj.getVolume()) &&
                !uniObj.getVolume().equals("0")){
            xmlCitation.setVolume(uniObj.getVolume());
        }

        return xmlCitation;
    }
}
