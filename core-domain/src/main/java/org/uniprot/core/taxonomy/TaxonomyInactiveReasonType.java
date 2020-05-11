package org.uniprot.core.taxonomy;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

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
