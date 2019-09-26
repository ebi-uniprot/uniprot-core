package org.uniprot.core.uniprot.taxonomy.builder;


import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismImpl;

/**
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
        addOrIgnoreNull(lineage, this.lineage);
        return this;
    }

    public OrganismBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public OrganismBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }


    public Organism build() {
        return new OrganismImpl(lineage, taxonId, evidences, scientificName, commonName, synonyms);
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

    @Override
    protected OrganismBuilder getThis() {
        return this;
    }
}