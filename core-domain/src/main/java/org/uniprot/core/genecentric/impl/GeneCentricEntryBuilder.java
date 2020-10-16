package org.uniprot.core.genecentric.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class GeneCentricEntryBuilder implements Builder<GeneCentricEntry> {

    private Protein canonicalProtein;
    private List<Protein> relatedProteins = new ArrayList<>();

    public @Nonnull GeneCentricEntryBuilder canonicalProtein(Protein canonicalProtein) {
        this.canonicalProtein = canonicalProtein;
        return this;
    }

    public @Nonnull GeneCentricEntryBuilder relatedProteinsSet(List<Protein> relatedProteins) {
        this.relatedProteins = Utils.modifiableList(relatedProteins);
        return this;
    }

    public @Nonnull GeneCentricEntryBuilder relatedProteinsAdd(Protein relatedProtein) {
        Utils.addOrIgnoreNull(relatedProtein, this.relatedProteins);
        return this;
    }

    public static @Nonnull GeneCentricEntryBuilder from(@Nonnull GeneCentricEntry instance) {
        return new GeneCentricEntryBuilder()
                .canonicalProtein(instance.getCanonicalProtein())
                .relatedProteinsSet(instance.getRelatedProteins());
    }

    @Nonnull
    @Override
    public GeneCentricEntry build() {
        return new GeneCentricEntryImpl(canonicalProtein, relatedProteins);
    }
}
