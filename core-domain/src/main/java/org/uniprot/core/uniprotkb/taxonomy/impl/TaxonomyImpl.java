package org.uniprot.core.uniprotkb.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class TaxonomyImpl extends AbstractOrganismNameImpl implements Taxonomy {

    private static final long serialVersionUID = -319775179301440772L;
    private final long taxonId;
    private final String mnemonic;

    TaxonomyImpl() {
        this(-1, null, null, null, null);
    }

    protected TaxonomyImpl(
            long taxonId,
            String scientificName,
            String commonName,
            List<String> synonyms,
            String mnemonic) {
        super(scientificName, commonName, synonyms);
        this.taxonId = taxonId;
        this.mnemonic = mnemonic;
    }

    @Override
    public long getTaxonId() {
        return taxonId;
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public boolean hasMnemonic() {
        return Utils.notNullNotEmpty(this.mnemonic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaxonomyImpl that = (TaxonomyImpl) o;
        return Objects.equals(taxonId, that.taxonId) && Objects.equals(mnemonic, that.mnemonic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taxonId, mnemonic);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTaxonId());
        sb.append(" ");
        sb.append(super.toString());
        return sb.toString();
    }
}
