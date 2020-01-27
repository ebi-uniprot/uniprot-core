package org.uniprot.core.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.impl.CanonicalProteinImpl;
import org.uniprot.core.util.Utils;

public class CanonicalProteinBuilder implements Builder<CanonicalProtein> {
    private Protein canonicalProtein;
    private List<Protein> relatedProteins = new ArrayList<>();

    public static @Nonnull CanonicalProteinBuilder newInstance() {
        return new CanonicalProteinBuilder();
    }

    @Override
    public @Nonnull CanonicalProtein build() {
        return new CanonicalProteinImpl(canonicalProtein, relatedProteins);
    }

    public @Nonnull CanonicalProteinBuilder relatedProteins(List<Protein> relatedProteins) {
        this.relatedProteins = Utils.modifiableList(relatedProteins);
        return this;
    }

    public @Nonnull CanonicalProteinBuilder addRelatedProtein(Protein relatedProtein) {
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
                .relatedProteins(instance.getRelatedProteins());
    }
}
