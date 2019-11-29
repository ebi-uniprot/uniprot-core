package org.uniprot.core.taxonomy;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum TaxonomyInactiveReasonType implements EnumDisplay<TaxonomyInactiveReasonType> {
    DELETED,
    MERGED;

    @Override
    public @Nonnull String toDisplayName() {
        return this.name();
    }
}
