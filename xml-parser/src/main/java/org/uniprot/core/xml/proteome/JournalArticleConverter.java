package org.uniprot.core.xml.proteome;

import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.CitationType;
import org.uniprot.core.xml.jaxb.proteome.DbReferenceType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

public class JournalArticleConverter implements Converter<CitationType, JournalArticle> {
    private final ObjectFactory xmlFactory;

    public JournalArticleConverter() {
        this(new ObjectFactory());
    }

    public JournalArticleConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public JournalArticle fromXml(CitationType journal) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        ReferenceConverterHelper.updateFromXmlCitaiton(journal, builder);
        builder.title(journal.getTitle())
                .firstPage(journal.getFirst())
                .lastPage(journal.getLast())
                .volume(journal.getVolume())
                .journalName(journal.getName())
                .citationCrossReferencesSet(
                        journal.getDbReference().stream()
                                .map(this::fromXml)
                                .collect(Collectors.toList()));
        return builder.build();
    }

    @Override
    public CitationType toXml(JournalArticle uniObj) {
        CitationType xmlJournal = xmlFactory.createCitationType();
        xmlJournal.setType(org.uniprot.core.citation.CitationType.JOURNAL_ARTICLE.getDisplayName());
        ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlJournal, uniObj);
        xmlJournal.setFirst(uniObj.getFirstPage());
        xmlJournal.setLast(uniObj.getLastPage());
        xmlJournal.setTitle(uniObj.getTitle());
        xmlJournal.setName(uniObj.getJournal().getName());
        xmlJournal.setVolume(uniObj.getVolume());
        uniObj.getCitationCrossReferences().stream()
                .map(this::toXml)
                .forEach(val -> xmlJournal.getDbReference().add(val));
        return xmlJournal;
    }

    private CrossReference<CitationDatabase> fromXml(DbReferenceType xmlRef) {
        CitationDatabase type = CitationDatabase.typeOf(xmlRef.getType());
        return new CrossReferenceBuilder<CitationDatabase>()
                .database(type)
                .id(xmlRef.getId())
                .build();
    }

    private DbReferenceType toXml(CrossReference<CitationDatabase> xref) {
        DbReferenceType dbReferenceType = xmlFactory.createDbReferenceType();
        dbReferenceType.setId(xref.getId());
        dbReferenceType.setType(xref.getDatabase().getName());
        return dbReferenceType;
    }
}
