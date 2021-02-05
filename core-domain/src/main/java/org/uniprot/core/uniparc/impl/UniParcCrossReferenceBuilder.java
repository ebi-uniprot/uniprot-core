package org.uniprot.core.uniparc.impl;

import java.time.LocalDate;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractCrossReferenceBuilder;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcCrossReferenceBuilder
        extends AbstractCrossReferenceBuilder<
                UniParcCrossReferenceBuilder, UniParcDatabase, UniParcCrossReference> {
    private int versionI;
    private Integer version;
    private boolean active;
    private LocalDate created;
    private LocalDate lastUpdated;
    private String geneName;
    private String proteinName;
    private Organism organism;
    private String chain;
    private String ncbiGi;
    private String proteomeId;
    private String component;

    @Override
    public @Nonnull UniParcCrossReference build() {
        return new UniParcCrossReferenceImpl(
                database,
                id,
                properties,
                versionI,
                version,
                active,
                created,
                lastUpdated,
                geneName,
                proteinName,
                organism,
                chain,
                ncbiGi,
                proteomeId,
                component);
    }

    public @Nonnull UniParcCrossReferenceBuilder versionI(int versionI) {
        this.versionI = versionI;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder version(Integer version) {
        this.version = version;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder created(LocalDate created) {
        this.created = created;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder geneName(String geneName) {
        this.geneName = geneName;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder proteinName(String proteinName) {
        this.proteinName = proteinName;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder organism(Organism taxonomy) {
        this.organism = taxonomy;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder chain(String chain) {
        this.chain = chain;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder ncbiGi(String ncbiGi) {
        this.ncbiGi = ncbiGi;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder proteomeId(String proteomeId) {
        this.proteomeId = proteomeId;
        return this;
    }

    public @Nonnull UniParcCrossReferenceBuilder component(String component) {
        this.component = component;
        return this;
    }

    public static @Nonnull UniParcCrossReferenceBuilder from(
            @Nonnull UniParcCrossReference instance) {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        AbstractCrossReferenceBuilder.init(builder, instance);
        return builder.versionI(instance.getVersionI())
                .version(instance.getVersion())
                .active(instance.isActive())
                .created(instance.getCreated())
                .lastUpdated(instance.getLastUpdated())
                .geneName(instance.getGeneName())
                .proteinName(instance.getProteinName())
                .organism(instance.getOrganism())
                .chain(instance.getChain())
                .ncbiGi(instance.getNcbiGi())
                .proteomeId(instance.getProteomeId())
                .component(instance.getComponent());
    }

    @Override
    protected @Nonnull UniParcCrossReferenceBuilder getThis() {
        return this;
    }
}
