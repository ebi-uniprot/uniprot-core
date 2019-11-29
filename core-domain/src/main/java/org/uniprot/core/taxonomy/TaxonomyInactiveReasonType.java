package org.uniprot.core.taxonomy;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum TaxonomyInactiveReasonType implements EnumDisplay<TaxonomyInactiveReasonType> {
    DELETED,
    MERGED;

    @Override
    public @Nonnull String toDisplayName() {
        return this.name();
    }
}
