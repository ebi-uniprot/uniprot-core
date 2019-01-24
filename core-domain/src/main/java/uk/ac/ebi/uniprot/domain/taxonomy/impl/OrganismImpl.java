package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.List;
import java.util.Objects;

public class OrganismImpl extends AbstractOrganismNameImpl implements Organism {

    private static final long serialVersionUID = 3285422222944186108L;
    private long taxonId;
    private List<Evidence> evidences;
    private List<String> lineage;

    private OrganismImpl() {
        this(new OrganismBuilder());
    }

    public OrganismImpl(OrganismBuilder builder) {
        super(builder);
        this.taxonId = builder.getTaxonId();
        this.evidences = builder.getEvidences();
        this.lineage = builder.getLineage();
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(getTaxonId() > 0) {
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
