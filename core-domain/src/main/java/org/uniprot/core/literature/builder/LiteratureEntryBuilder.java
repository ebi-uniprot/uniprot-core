package org.uniprot.core.literature.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.impl.JournalImpl;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureEntryImpl;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
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

    public @Nonnull LiteratureEntryBuilder pubmedId(Long pubmedId) {
        this.pubmedId = pubmedId;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder doiId(String doiId) {
        this.doiId = doiId;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder title(String title) {
        this.title = Utils.emptyOrString(title);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder authoringGroup(List<String> authoringGroup) {
        this.authoringGroup = Utils.modifiableList(authoringGroup);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder addAuthoringGroup(String authoringGroup) {
        Utils.addOrIgnoreNull(authoringGroup, this.authoringGroup);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder authors(List<Author> authors) {
        this.authors = Utils.modifiableList(authors);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder addAuthor(Author author) {
        Utils.addOrIgnoreNull(author, this.authors);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder completeAuthorList(boolean completeAuthorList) {
        this.completeAuthorList = completeAuthorList;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder publicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder journal(Journal journal) {
        this.journal = journal;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder journal(String journalName) {
        if (Utils.notNullOrEmpty(journalName)) {
            this.journal = new JournalImpl(journalName);
        }
        return this;
    }

    public @Nonnull LiteratureEntryBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder literatureAbstract(String literatureAbstract) {
        this.literatureAbstract = literatureAbstract;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder literatureMappedReference(
            List<LiteratureMappedReference> literatureMappedReference) {
        this.literatureMappedReference = Utils.modifiableList(literatureMappedReference);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder addLiteratureMappedReference(
            LiteratureMappedReference literatureMappedReference) {
        Utils.addOrIgnoreNull(literatureMappedReference, this.literatureMappedReference);
        return this;
    }

    public @Nonnull LiteratureEntryBuilder statistics(LiteratureStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    @Override
    public @Nonnull LiteratureEntry build() {
        return new LiteratureEntryImpl(
                pubmedId,
                doiId,
                title,
                authoringGroup,
                authors,
                completeAuthorList,
                publicationDate,
                journal,
                firstPage,
                lastPage,
                volume,
                literatureAbstract,
                literatureMappedReference,
                statistics);
    }

    @Override
    public @Nonnull LiteratureEntryBuilder from(@Nonnull LiteratureEntry instance) {
        LiteratureEntryBuilder builder =
                new LiteratureEntryBuilder()
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
