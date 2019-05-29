package uk.ac.ebi.uniprot.domain.taxonomy;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum TaxonomyInactiveReasonType implements EnumDisplay<TaxonomyInactiveReasonType> {

    DELETED,MERGED;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
