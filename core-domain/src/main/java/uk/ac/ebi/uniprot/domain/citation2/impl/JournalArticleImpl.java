package uk.ac.ebi.uniprot.domain.citation2.impl;

import uk.ac.ebi.uniprot.domain.citation2.CitationType;
import uk.ac.ebi.uniprot.domain.citation2.Journal;
import uk.ac.ebi.uniprot.domain.citation2.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation2.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;

public class JournalArticleImpl extends AbstractCitationImpl implements JournalArticle {

    private Journal journal;
    private String firstPage;
    private String lastPage;
    private String volume;

    private JournalArticleImpl() {
        super(CitationType.JOURNAL_ARTICLE, Collections.emptyList(), Collections.emptyList(),
              null, null, null);
        this.firstPage = "";
        this.lastPage = "";
        this.volume = "";
    }

    public JournalArticleImpl(JournalArticleBuilder builder) {
        super(CitationType.JOURNAL_ARTICLE, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
        this.journal = new JournalImpl(builder.getJournalName());
        this.firstPage = Utils.resetNull(builder.getFirstPage());
        this.lastPage = Utils.resetNull(builder.getLastPage());
        this.volume = Utils.resetNull(builder.getVolume());
    }

    @Override
    public Journal getJournal() {
        return journal;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
        result = prime * result + ((journal == null) ? 0 : journal.hashCode());
        result = prime * result + ((lastPage == null) ? 0 : lastPage.hashCode());
        result = prime * result + ((volume == null) ? 0 : volume.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        JournalArticleImpl other = (JournalArticleImpl) obj;
        if (firstPage == null) {
            if (other.firstPage != null)
                return false;
        } else if (!firstPage.equals(other.firstPage))
            return false;
        if (journal == null) {
            if (other.journal != null)
                return false;
        } else if (!journal.equals(other.journal))
            return false;
        if (lastPage == null) {
            if (other.lastPage != null)
                return false;
        } else if (!lastPage.equals(other.lastPage))
            return false;
        if (volume == null) {
            if (other.volume != null)
                return false;
        } else if (!volume.equals(other.volume))
            return false;
        return true;
    }
}
