package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Literature;
import org.uniprot.core.citation.impl.LiteratureImpl;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
public final class LiteratureBuilder
        extends AbstractJournalArticleBuilder<LiteratureBuilder, Literature> {

    private boolean completeAuthorList = true;
    private String literatureAbstract = "";

    public @Nonnull LiteratureBuilder completeAuthorList(boolean completeAuthorList) {
        this.completeAuthorList = completeAuthorList;
        return this;
    }

    public @Nonnull LiteratureBuilder literatureAbstract(String literatureAbstract) {
        this.literatureAbstract = literatureAbstract;
        return this;
    }

    public static @Nonnull LiteratureBuilder from(@Nonnull Literature instance) {
        LiteratureBuilder builder = new LiteratureBuilder();
        init(builder, instance);
        builder.completeAuthorList(instance.isCompleteAuthorList());
        builder.literatureAbstract(instance.getLiteratureAbstract());
        return builder;
    }

    @Nonnull
    @Override
    public Literature build() {
        return new LiteratureImpl(
                authoringGroups,
                authors,
                citationXrefs,
                title,
                publicationDate,
                journalName,
                firstPage,
                lastPage,
                volume,
                literatureAbstract,
                completeAuthorList);
    }

    @Nonnull
    @Override
    protected LiteratureBuilder getThis() {
        return this;
    }
}
