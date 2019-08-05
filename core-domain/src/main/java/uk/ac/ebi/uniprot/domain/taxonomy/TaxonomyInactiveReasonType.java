package uk.ac.ebi.uniprot.domain.taxonomy;

import org.uniprot.core.common.EnumDisplay;

public enum TaxonomyInactiveReasonType implements EnumDisplay<TaxonomyInactiveReasonType> {

    DELETED,MERGED;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
