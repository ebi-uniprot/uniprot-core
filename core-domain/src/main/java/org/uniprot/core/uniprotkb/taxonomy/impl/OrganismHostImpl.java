package org.uniprot.core.uniprotkb.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;

/** @author lgonzales */
public class OrganismHostImpl extends AbstractOrganismNameImpl implements OrganismHost {

    private static final long serialVersionUID = 6516703868320522667L;
    private long taxonId;

    OrganismHostImpl() {
        this(-1, null, null, null);
    }

    OrganismHostImpl(
            long taxonId, String scientificName, String commonName, List<String> synonyms) {
        super(scientificName, commonName, synonyms);
        this.taxonId = taxonId;
    }

    @Override
    public long getTaxonId() {
        return taxonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrganismHostImpl that = (OrganismHostImpl) o;
        return taxonId == that.taxonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taxonId);
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
