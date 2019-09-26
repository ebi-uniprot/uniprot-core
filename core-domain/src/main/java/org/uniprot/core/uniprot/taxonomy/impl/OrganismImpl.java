package org.uniprot.core.uniprot.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import static java.util.Collections.emptyList;
import static org.uniprot.core.util.Utils.unmodifiableList;

public class OrganismImpl extends AbstractOrganismNameImpl implements Organism {
    private static final long serialVersionUID = 3285422222944186108L;
    private long taxonId;
    private List<Evidence> evidences;
    private List<String> lineage;

    private OrganismImpl() {
        this(emptyList(), 0L, emptyList(), null, null, emptyList());
    }

    public OrganismImpl(List<String> lineage, long taxonId, List<Evidence> evidences,
                        String scientificName, String commonName, List<String> synonyms) {
        super(scientificName, commonName, synonyms);
        this.lineage = unmodifiableList(lineage);
        this.taxonId = taxonId;
        this.evidences = unmodifiableList(evidences);
    }

    @Override
    public long getTaxonId() {
        return taxonId;
    }

    @Override
    public List<String> getLineage() {
        return lineage;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getTaxonId() > 0) {
            sb.append(getTaxonId());
            sb.append(" ");
        }
        sb.append(super.toString());
        List<String> lineage = this.getLineage();
        if (!lineage.isEmpty()) {
            sb.append(" (")
                    .append(String.join(", ", lineage))
                    .append(")");
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrganismImpl organism = (OrganismImpl) o;
        return taxonId == organism.taxonId &&
                Objects.equals(evidences, organism.evidences) &&
                Objects.equals(lineage, organism.lineage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taxonId, evidences, lineage);
    }
}
