package uk.ac.ebi.uniprot.domain.literature.builder;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalImpl;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;
import uk.ac.ebi.uniprot.domain.literature.LiteratureMappedReference;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;
import uk.ac.ebi.uniprot.domain.literature.impl.LiteratureEntryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgonzales
 */
public class LiteratureEntryBuilder implements Builder<LiteratureEntryBuilder, LiteratureEntry> {

    private Long pubmedId;
    private String doiId;
    private String title = "";
    private List<String> authoringGroup = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private boolean completeAuthorList = true;
    private PublicationDate publicationDate;
    private Journal journal;
    private String firstPage;
    private String lastPage;
    private String volume;
    private String literatureAbstract = "";
    private List<LiteratureMappedReference> literatureMappedReference = new ArrayList<>();
    private LiteratureStatistics statistics;

    public LiteratureEntryBuilder pubmedId(Long pubmedId) {
        this.pubmedId = pubmedId;
        return this;
    }

    public LiteratureEntryBuilder doiId(String doiId) {
        this.doiId = doiId;
        return this;
    }

    public LiteratureEntryBuilder title(String title) {
        this.title = Utils.nullToEmpty(title);
        return this;
    }

    public LiteratureEntryBuilder authoringGroup(List<String> authoringGroup) {
        this.authoringGroup = Utils.nonNullList(authoringGroup);
        return this;
    }

    public LiteratureEntryBuilder addAuthoringGroup(String authoringGroup) {
        Utils.nonNullAdd(authoringGroup, this.authoringGroup);
        return this;
    }

    public LiteratureEntryBuilder authors(List<Author> authors) {
        this.authors = Utils.nonNullList(authors);
        return this;
    }

    public LiteratureEntryBuilder addAuthor(Author author) {
        Utils.nonNullAdd(author, this.authors);
        return this;
    }

    public LiteratureEntryBuilder completeAuthorList(boolean completeAuthorList) {
        this.completeAuthorList = completeAuthorList;
        return this;
    }

    public LiteratureEntryBuilder publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public LiteratureEntryBuilder journal(Journal journal) {
        this.journal = journal;
        return this;
    }

    public LiteratureEntryBuilder journal(String journalName) {
        if (Utils.notEmpty(journalName)) {
            this.journal = new JournalImpl(journalName);
        }
        return this;
    }

    public LiteratureEntryBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public LiteratureEntryBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public LiteratureEntryBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    public LiteratureEntryBuilder literatureAbstract(String literatureAbstract) {
        this.literatureAbstract = literatureAbstract;
        return this;
    }

    public LiteratureEntryBuilder literatureMappedReference(List<LiteratureMappedReference> literatureMappedReference) {
        this.literatureMappedReference = Utils.nonNullList(literatureMappedReference);
        return this;
    }

    public LiteratureEntryBuilder addLiteratureMappedReference(LiteratureMappedReference literatureMappedReference) {
        Utils.nonNullAdd(literatureMappedReference, this.literatureMappedReference);
        return this;
    }

    public LiteratureEntryBuilder statistics(LiteratureStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    @Override
    public LiteratureEntry build() {
        return new LiteratureEntryImpl(pubmedId, doiId, title, authoringGroup, authors, completeAuthorList,
                publicationDate, journal, firstPage, lastPage, volume, literatureAbstract, literatureMappedReference, statistics);
    }

    @Override
    public LiteratureEntryBuilder from(LiteratureEntry instance) {
        LiteratureEntryBuilder builder = new LiteratureEntryBuilder()
                .pubmedId(instance.getPubmedId())
                .doiId(instance.getDoiId())
                .title(instance.getTitle())
                .authoringGroup(instance.getAuthoringGroup())
                .authors(instance.getAuthors())
                .completeAuthorList(instance.isCompleteAuthorList())
                .publicationDate(instance.getPublicationDate())
                .journal(instance.getJournal())
                .volume(instance.getVolume())
                .firstPage(instance.getFirstPage())
                .lastPage(instance.getLastPage())
                .literatureAbstract(instance.getLiteratureAbstract())
                .literatureMappedReference(instance.getLiteratureMappedReferences())
                .statistics(instance.getStatistics());
        return builder;
    }
}
