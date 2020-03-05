package org.uniprot.core.uniparc.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class UniParcCrossReferenceImpl extends CrossReferenceImpl<UniParcDatabase>
        implements UniParcCrossReference {
    private static final long serialVersionUID = 1387909162449408089L;
    private int versionI;
    private Integer version;
    private boolean active;
    private LocalDate created;
    private LocalDate lastUpdated;

    protected UniParcCrossReferenceImpl() {
        super(null, "", Collections.emptyList());
    }

    public UniParcCrossReferenceImpl(
            UniParcDatabase databaseType,
            String id,
            List<Property> properties,
            int versionI,
            Integer version,
            boolean active,
            LocalDate created,
            LocalDate lastUpdated) {
        super(databaseType, id, properties);
        this.versionI = versionI;
        this.version = version;
        this.active = active;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int getVersionI() {
        return versionI;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public LocalDate getCreated() {
        return created;
    }

    @Override
    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniParcCrossReferenceImpl that = (UniParcCrossReferenceImpl) o;
        return (this.versionI == that.versionI)
                && Objects.equals(version, that.version)
                && (this.active == that.active)
                && Objects.equals(created, that.created)
                && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), versionI, version, active, created, lastUpdated);
    }
}
