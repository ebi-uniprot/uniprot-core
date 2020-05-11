package org.uniprot.core.taxonomy;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum TaxonomyInactiveReasonType implements EnumDisplay {
    DELETED,
    MERGED;

    public @Nonnull String getName() {
        return this.name();
    }

    public static @Nonnull TaxonomyInactiveReasonType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, TaxonomyInactiveReasonType.class);
    }
}
