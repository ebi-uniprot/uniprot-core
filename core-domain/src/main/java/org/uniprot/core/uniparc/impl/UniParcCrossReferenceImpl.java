package org.uniprot.core.uniparc.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class UniParcCrossReferenceImpl extends CrossReferenceImpl<UniParcDatabase>
        implements UniParcCrossReference {
    private static final long serialVersionUID = 1387909162449408089L;
    private final int versionI;
    private final Integer version;
    private final boolean active;
    private final LocalDate created;
    private final LocalDate lastUpdated;
    private final String geneName;
    private final String proteinName;
    private final Organism taxonomy;
    private final String chain;
    private final String ncbiGi;
    private final String proteomeId;
    private final String component;

    UniParcCrossReferenceImpl() {
        this(null, null, null, 0, null, false, null,null, null, null, null, null, null, null, null);
    }

    UniParcCrossReferenceImpl(
            UniParcDatabase database,
            String id,
            List<Property> properties,
            int versionI,
            Integer version,
            boolean active,
            LocalDate created,
            LocalDate lastUpdated,
            String geneName,
            String proteinName,
            Organism taxonomy,
            String chain,
            String ncbiGi,
            String proteomeId,
            String component) {
        super(database, id, properties);
        this.versionI = versionI;
        this.version = version;
        this.active = active;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.geneName = geneName;
        this.proteinName = proteinName;
        this.taxonomy = taxonomy;
        this.chain = chain;
        this.ncbiGi = ncbiGi;
        this.proteomeId = proteomeId;
        this.component = component;
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
    public String getGeneName() {
        return geneName;
    }

    @Override
    public String getProteinName() {
        return proteinName;
    }

    @Override
    public Organism getTaxonomy() {
        return taxonomy;
    }

    @Override
    public String getChain() {
        return chain;
    }

    @Override
    public String getNcbiGi() {
        return ncbiGi;
    }

    @Override
    public String getProteomeId() {
        return proteomeId;
    }

    @Override
    public String getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniParcCrossReferenceImpl)) return false;
        if (!super.equals(o)) return false;
        UniParcCrossReferenceImpl that = (UniParcCrossReferenceImpl) o;
        return getVersionI() == that.getVersionI() &&
                isActive() == that.isActive() &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getCreated(), that.getCreated()) &&
                Objects.equals(getLastUpdated(), that.getLastUpdated()) &&
                Objects.equals(getGeneName(), that.getGeneName()) &&
                Objects.equals(getProteinName(), that.getProteinName()) &&
                Objects.equals(getTaxonomy(), that.getTaxonomy()) &&
                Objects.equals(getChain(), that.getChain()) &&
                Objects.equals(getNcbiGi(), that.getNcbiGi()) &&
                Objects.equals(getProteomeId(), that.getProteomeId()) &&
                Objects.equals(getComponent(), that.getComponent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVersionI(), getVersion(),
                isActive(), getCreated(), getLastUpdated(), getGeneName(),
                getProteinName(), getTaxonomy(), getChain(), getNcbiGi(),
                getProteomeId(), getComponent());
    }
}
