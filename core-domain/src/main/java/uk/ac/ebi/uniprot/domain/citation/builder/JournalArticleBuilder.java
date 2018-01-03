package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.List;

public final class JournalArticleBuilder extends AbstractCitationBuilder<JournalArticle> {

    public static JournalArticleBuilder newInstance() {
        return new JournalArticleBuilder();
    }

    private String journalName;
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";


    public JournalArticle build() {
        return new JournalArticleImpl(authoringGroups, authors,
                xrefs, title, publicationDate,
                journalName, firstPage, lastPage, volume);
    }

    public JournalArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }


    public JournalArticleBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public JournalArticleBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public JournalArticleBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    
    class JournalArticleImpl extends AbstractCitationImpl implements JournalArticle {
        
        private final String journalName;
        private final String firstPage;
        private final String lastPage;
        private final String volume;
        
        JournalArticleImpl( List<String> authoringGroups, List<Author> authors, CitationXrefs xrefs,
            String title, PublicationDate publicationDate,
            String journalName, String firstPage, String lastPage, String volume) {
            super(CitationType.JOURNAL_ARTICLE, authoringGroups, authors, xrefs, title, publicationDate);
            this.journalName = journalName;
            this.firstPage = firstPage;
            this.lastPage = lastPage;
            this.volume = volume;

        }

        @Override
        public String getJournalName() {
            return journalName;
        }

        @Override
        public String getFirstPage() {
           return firstPage;
        }

        @Override
        public String getLastPage() {
           return lastPage;
        }

        @Override
        public String getVolume() {
           return volume;
        }

    }

}
