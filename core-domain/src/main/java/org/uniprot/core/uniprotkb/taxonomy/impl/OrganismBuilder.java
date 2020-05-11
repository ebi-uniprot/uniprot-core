package org.uniprot.core.uniprotkb.taxonomy.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class OrganismBuilder extends AbstractOrganismNameBuilder<OrganismBuilder, Organism> {
    private long taxonId;
    private List<Evidence> evidences = new ArrayList<>();
    private List<String> lineages = new ArrayList<>();

    public @Nonnull OrganismBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull OrganismBuilder lineagesSet(List<String> lineage) {
        this.lineages = modifiableList(lineage);
        return this;
    }

    public @Nonnull OrganismBuilder lineagesAdd(String lineage) {
        addOrIgnoreNull(lineage, this.lineages);
        return this;
    }

    public @Nonnull OrganismBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull OrganismBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull Organism build() {
        return new OrganismImpl(lineages, taxonId, evidences, scientificName, commonName, synonyms);
    }

    public static @Nonnull OrganismBuilder from(@Nonnull Organism instance) {
        OrganismBuilder builder = new OrganismBuilder();
        AbstractOrganismNameBuilder.init(builder, instance);
        builder.taxonId(instance.getTaxonId())
                .evidencesSet(instance.getEvidences())
                .lineagesSet(instance.getLineages());
        return builder;
    }

    @Override
    protected @Nonnull OrganismBuilder getThis() {
        return this;
    }
}
