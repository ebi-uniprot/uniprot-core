package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;

import java.util.Objects;

/**
 *
 * @author lgonzales
 */
public class OrganismHostImpl extends AbstractOrganismNameImpl implements OrganismHost {

    private static final long serialVersionUID = 6516703868320522667L;
    private long taxonId;

    private OrganismHostImpl(){
        this(new OrganismHostBuilder());
    }

    public OrganismHostImpl(OrganismHostBuilder builder){
        super(builder);
        this.taxonId = builder.getTaxonId();
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
