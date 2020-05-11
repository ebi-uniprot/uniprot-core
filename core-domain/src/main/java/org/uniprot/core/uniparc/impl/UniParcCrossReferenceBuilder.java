package org.uniprot.core.uniparc.impl;

import org.uniprot.core.impl.AbstractCrossReferenceBuilder;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;

import java.time.LocalDate;

import javax.annotation.Nonnull;

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

    @Override
    public @Nonnull UniParcCrossReference build() {
        return new UniParcCrossReferenceImpl(
                database, id, properties, versionI, version, active, created, lastUpdated);
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

    public static @Nonnull UniParcCrossReferenceBuilder from(
            @Nonnull UniParcCrossReference instance) {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        AbstractCrossReferenceBuilder.init(builder, instance);
        return builder.versionI(instance.getVersionI())
                .version(instance.getVersion())
                .active(instance.isActive())
                .created(instance.getCreated())
                .lastUpdated(instance.getLastUpdated());
    }

    @Override
    protected @Nonnull UniParcCrossReferenceBuilder getThis() {
        return this;
    }
}
