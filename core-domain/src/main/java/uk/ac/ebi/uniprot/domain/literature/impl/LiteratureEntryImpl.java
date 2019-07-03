package uk.ac.ebi.uniprot.domain.literature.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;
import uk.ac.ebi.uniprot.domain.literature.LiteratureMappedReference;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;

import java.util.List;
import java.util.Objects;

/**
 * @author lgonzales
 */
public class LiteratureEntryImpl implements LiteratureEntry {

    private static final long serialVersionUID = -1925700851366460681L;

    private String pubmedId;
    private String doiId;
    private String title;
    private List<String> authoringGroup;
    private List<Author> authors;
    private boolean completeAuthorList;
    private PublicationDate publicationDate;
    private Journal journal;
    private String firstPage;
    private String lastPage;
    private String volume;
    private String literatureAbstract;
    private List<LiteratureMappedReference> literatureMappedReferences;
    private LiteratureStatistics statistics;

    private LiteratureEntryImpl() {
        this(null, null, null, null, null, false, null, null, null, null, null, null, null, null);
    }

    public LiteratureEntryImpl(String pubmedId, String doiId, String title, List<String> authoringGroup,
                               List<Author> authors, boolean completeAuthorList, PublicationDate publicationDate, Journal journal, String firstPage,
                               String lastPage, String volume, String literatureAbstract,
                               List<LiteratureMappedReference> literatureMappedReferences, LiteratureStatistics statistics) {
        this.pubmedId = pubmedId;
        this.doiId = Utils.nullToEmpty(doiId);
        this.title = Utils.nullToEmpty(title);
        this.authoringGroup = Utils.nonNullList(authoringGroup);
        this.authors = Utils.nonNullList(authors);
        this.completeAuthorList = completeAuthorList;
        this.publicationDate = publicationDate;
        this.journal = journal;
        this.firstPage = Utils.nullToEmpty(firstPage);
        this.lastPage = Utils.nullToEmpty(lastPage);
        this.volume = Utils.nullToEmpty(volume);
        this.literatureAbstract = Utils.nullToEmpty(literatureAbstract);
        this.literatureMappedReferences = Utils.nonNullList(literatureMappedReferences);
        this.statistics = statistics;
    }


    @Override
    public String getPubmedId() {
        return pubmedId;
    }

    @Override
    public String getDoiId() {
        return doiId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getAuthoringGroup() {
        return authoringGroup;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public boolean isCompleteAuthorList() {
        return completeAuthorList;
    }

    @Override
    public PublicationDate getPublicationDate() {
        return publicationDate;
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
    public String getLiteratureAbstract() {
        return literatureAbstract;
    }

    @Override
    public List<LiteratureMappedReference> getLiteratureMappedReferences() {
        return literatureMappedReferences;
    }

    @Override
    public LiteratureStatistics getStatistics() {
        return statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureEntryImpl that = (LiteratureEntryImpl) o;
        return isCompleteAuthorList() == that.isCompleteAuthorList() &&
                Objects.equals(getPubmedId(), that.getPubmedId()) &&
                Objects.equals(getDoiId(), that.getDoiId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAuthoringGroup(), that.getAuthoringGroup()) &&
                Objects.equals(getAuthors(), that.getAuthors()) &&
                Objects.equals(getPublicationDate(), that.getPublicationDate()) &&
                Objects.equals(getJournal(), that.getJournal()) &&
                Objects.equals(getFirstPage(), that.getFirstPage()) &&
                Objects.equals(getLastPage(), that.getLastPage()) &&
                Objects.equals(getVolume(), that.getVolume()) &&
                Objects.equals(getLiteratureAbstract(), that.getLiteratureAbstract()) &&
                Objects.equals(getStatistics(), that.getStatistics()) &&
                Objects.equals(getLiteratureMappedReferences(), that.getLiteratureMappedReferences());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPubmedId(), getDoiId(), getTitle(), getAuthoringGroup(), getAuthors(),
                isCompleteAuthorList(), getPublicationDate(), getJournal(), getFirstPage(), getLastPage(),
                getVolume(), getLiteratureAbstract(), getStatistics(), getLiteratureMappedReferences());
    }

    @Override
    public String toString() {
        return "LiteratureEntryImpl{" +
                "pubmedId='" + pubmedId + '\'' +
                ", doiId='" + doiId + '\'' +
                ", title='" + title + '\'' +
                ", authoringGroup=" + authoringGroup +
                ", authors=" + authors +
                ", completeAuthorList=" + completeAuthorList +
                ", publicationDate=" + publicationDate +
                ", journal=" + journal +
                ", firstPage='" + firstPage + '\'' +
                ", lastPage='" + lastPage + '\'' +
                ", volume='" + volume + '\'' +
                ", literatureAbstract='" + literatureAbstract + '\'' +
                ", statistics=" + statistics +
                ", literatureMappedReferences=" + literatureMappedReferences +
                '}';
    }
}
