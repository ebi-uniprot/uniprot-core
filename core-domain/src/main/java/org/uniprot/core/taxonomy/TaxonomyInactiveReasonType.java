package org.uniprot.core.taxonomy;

import org.uniprot.core.util.EnumDisplay;

public enum TaxonomyInactiveReasonType implements EnumDisplay<TaxonomyInactiveReasonType> {

    DELETED,MERGED;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
