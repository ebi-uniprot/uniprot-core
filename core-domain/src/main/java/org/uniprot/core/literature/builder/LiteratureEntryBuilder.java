package org.uniprot.core.literature.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureEntryImpl;

/** @author lgonzales */
public class LiteratureEntryBuilder implements Builder<LiteratureEntry> {

    private Citation citation;
    private LiteratureStatistics statistics;

    public @Nonnull LiteratureEntryBuilder citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public @Nonnull LiteratureEntryBuilder statistics(LiteratureStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    @Override
    public @Nonnull LiteratureEntry build() {
        return new LiteratureEntryImpl(citation, statistics);
    }

    public static @Nonnull LiteratureEntryBuilder from(@Nonnull LiteratureEntry instance) {
        return new LiteratureEntryBuilder()
                .citation(instance.getCitation())
                .statistics(instance.getStatistics());
    }
}
