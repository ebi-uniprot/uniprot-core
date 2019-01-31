package uk.ac.ebi.uniprot.domain.taxonomy.builder;


import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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
        this.lineage = nonNullList(lineage);
        return this;
    }

    public OrganismBuilder addLineage(String lineage) {
        nonNullAdd(lineage, this.lineage);
        return this;
    }

    public OrganismBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public OrganismBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }


    public Organism build() {
        return new OrganismImpl(this);
    }


    public OrganismBuilder from(Organism instance) {
        evidences.clear();
        lineage.clear();
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