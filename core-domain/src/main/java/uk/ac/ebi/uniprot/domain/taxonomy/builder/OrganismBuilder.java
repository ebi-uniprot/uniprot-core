package uk.ac.ebi.uniprot.domain.taxonomy.builder;


import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lgonzales
 */
public class OrganismBuilder extends AbstractOrganismNameBuilder<OrganismBuilder, Organism> {

    private long taxonId;
    private List<Evidence> evidences = new ArrayList<>();
    private List<String> lineage = new ArrayList<>();

    public OrganismBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public OrganismBuilder lineage(List<String> lineage) {
        this.lineage.addAll(lineage);
        return this;
    }

    public OrganismBuilder addLineage(String lineage) {
        this.lineage.add(lineage);
        return this;
    }

    public OrganismBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public OrganismBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }


    public Organism build() {
        return new OrganismImpl(this);
    }


    public OrganismBuilder from(Organism instance) {
        this.taxonId(instance.getTaxonId());
        this.evidences(instance.getEvidences());
        this.scientificName(instance.getScientificName());
        this.commonName(instance.getCommonName());
        this.synonyms(instance.getSynonyms());
        this.lineage(instance.getLineage());
        return this;
    }

    public long getTaxonId() {
        return taxonId;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public List<String> getLineage() {
        return lineage;
    }

    @Override
    protected OrganismBuilder getThis() {
        return this;
    }
}