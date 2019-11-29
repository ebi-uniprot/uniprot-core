package org.uniprot.core.uniparc.builder;

import java.time.LocalDate;

import org.uniprot.core.builder.AbstractDBCrossReferenceBuilder;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.impl.UniParcDBCrossReferenceImpl;

import javax.annotation.Nonnull;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcDBCrossReferenceBuilder
        extends AbstractDBCrossReferenceBuilder<
                UniParcDBCrossReferenceBuilder, UniParcDatabaseType, UniParcDBCrossReference> {
    private int versionI;
    private Integer version;
    private boolean active;
    private LocalDate created;
    private LocalDate lastUpdated;

    @Override
    public @Nonnull UniParcDBCrossReference build() {
        return new UniParcDBCrossReferenceImpl(
                databaseType, id, properties, versionI, version, active, created, lastUpdated);
    }

    public @Nonnull UniParcDBCrossReferenceBuilder versionI(int versionI) {
        this.versionI = versionI;
        return this;
    }

    public @Nonnull UniParcDBCrossReferenceBuilder version(Integer version) {
        this.version = version;
        return this;
    }

    public @Nonnull UniParcDBCrossReferenceBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public @Nonnull UniParcDBCrossReferenceBuilder created(LocalDate created) {
        this.created = created;
        return this;
    }

    public @Nonnull UniParcDBCrossReferenceBuilder lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    @Override
    public @Nonnull UniParcDBCrossReferenceBuilder from(@Nonnull UniParcDBCrossReference instance) {
        return super.from(instance)
                .versionI(instance.getVersionI())
                .version(instance.getVersion())
                .active(instance.isActive())
                .created(instance.getCreated())
                .lastUpdated(instance.getLastUpdated());
    }

    @Override
    protected UniParcDBCrossReferenceBuilder getThis() {
        return this;
    }
}
