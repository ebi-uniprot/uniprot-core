package org.uniprot.core.citation.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
public class LiteratureImpl extends AbstractJournalArticleImpl implements Literature {

    private static final long serialVersionUID = 5322959407211153467L;
    private boolean completeAuthorList;
    private String literatureAbstract;

    // no arg constructor for JSON deserialization
    LiteratureImpl() {
        this(
                emptyList(),
                emptyList(),
                emptyList(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false);
    }

    public LiteratureImpl(
            List<String> authoringGroup,
            List<Author> authors,
            List<CrossReference<CitationDatabase>> citationCrossReferences,
            String title,
            PublicationDate publicationDate,
            String journalName,
            String firstPage,
            String lastPage,
            String volume,
            String literatureAbstract,
            boolean completeAuthorList) {
        super(
                CitationType.LITERATURE,
                authoringGroup,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                journalName,
                firstPage,
                lastPage,
                volume);
        this.completeAuthorList = completeAuthorList;
        this.literatureAbstract = Utils.emptyOrString(literatureAbstract);
    }

    @Override
    public Long getPubmedId() {
        Optional<CrossReference<CitationDatabase>> result =
                getCitationCrossReferenceByType(CitationDatabase.PUBMED);
        return result.map(CrossReference::getId).map(Long::valueOf).orElse(0L);
    }

    @Override
    public String getDoiId() {
        Optional<CrossReference<CitationDatabase>> result =
                getCitationCrossReferenceByType(CitationDatabase.DOI);
        return result.map(CrossReference::getId).orElse("");
    }

    @Override
    public boolean isCompleteAuthorList() {
        return completeAuthorList;
    }

    @Override
    public String getLiteratureAbstract() {
        return literatureAbstract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LiteratureImpl that = (LiteratureImpl) o;
        return isCompleteAuthorList() == that.isCompleteAuthorList()
                && Objects.equals(getLiteratureAbstract(), that.getLiteratureAbstract());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isCompleteAuthorList(), getLiteratureAbstract());
    }
}
