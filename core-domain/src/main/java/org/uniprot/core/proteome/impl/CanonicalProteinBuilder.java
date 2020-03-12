package org.uniprot.core.proteome.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.util.Utils;

public class CanonicalProteinBuilder implements Builder<CanonicalProtein> {
    private Protein canonicalProtein;
    private List<Protein> relatedProteins = new ArrayList<>();

    @Override
    public @Nonnull CanonicalProtein build() {
        return new CanonicalProteinImpl(canonicalProtein, relatedProteins);
    }

    public @Nonnull CanonicalProteinBuilder relatedProteinsSet(List<Protein> relatedProteins) {
        this.relatedProteins = Utils.modifiableList(relatedProteins);
        return this;
    }

    public @Nonnull CanonicalProteinBuilder relatedProteinsAdd(Protein relatedProtein) {
        Utils.addOrIgnoreNull(relatedProtein, relatedProteins);
        return this;
    }

    public @Nonnull CanonicalProteinBuilder canonicalProtein(Protein canonicalProtein) {
        this.canonicalProtein = canonicalProtein;
        return this;
    }

    public static @Nonnull CanonicalProteinBuilder from(@Nonnull CanonicalProtein instance) {
        return new CanonicalProteinBuilder()
                .canonicalProtein(instance.getCanonicalProtein())
                .relatedProteinsSet(instance.getRelatedProteins());
    }
}