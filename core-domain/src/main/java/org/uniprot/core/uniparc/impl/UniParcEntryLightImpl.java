package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.CommonOrganism;
import org.uniprot.core.uniparc.Proteome;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.util.*;

public class UniParcEntryLightImpl implements UniParcEntryLight {

    private static final long serialVersionUID = 6932484977764108673L;
    private final String uniParcId;
    private final int crossReferenceCount;
    private final List<CommonOrganism> commonTaxons;
    private final Set<String> uniProtKBAccessions;
    private final  Sequence sequence;
    private final  List<SequenceFeature> sequenceFeatures;
    private final LocalDate oldestCrossRefCreated;
    private final LocalDate mostRecentCrossRefUpdated;

    private final Set<Organism> organisms;
    private final Set<String> proteinNames;
    private final Set<String> geneNames;
    private final Set<Proteome> proteomes;

    private final Map<String, Object> extraAttributes;

    UniParcEntryLightImpl() {
        this(null, 0, null, null, null, null, null, null);
    }

    UniParcEntryLightImpl(String uniParcId, int crossReferenceCount,
                          List<CommonOrganism> commonTaxons, LinkedHashSet<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated) {
        this(uniParcId, crossReferenceCount, commonTaxons, uniProtKBAccessions,
                sequence, sequenceFeatures, oldestCrossRefCreated, mostRecentCrossRefUpdated,
                null, null, null, null, null);
    }
    UniParcEntryLightImpl(String uniParcId, int crossReferenceCount,
                          List<CommonOrganism> commonTaxons, Set<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated,
                          Set<Organism> organisms, Set<String> proteinNames, Set<String> geneNames,
                          Set<Proteome> proteomes, Map<String, Object> extraAttributes) {
        this.uniParcId = uniParcId;
        this.crossReferenceCount = crossReferenceCount;
        this.commonTaxons = Utils.unmodifiableList(commonTaxons);
        this.uniProtKBAccessions = Utils.unmodifiableSet(uniProtKBAccessions);
        this.sequence = sequence;
        this.sequenceFeatures = Utils.unmodifiableList(sequenceFeatures);
        this.oldestCrossRefCreated = oldestCrossRefCreated;
        this.mostRecentCrossRefUpdated = mostRecentCrossRefUpdated;
        this.organisms = Utils.unmodifiableSet(organisms);
        this.proteinNames = Utils.unmodifiableSet(proteinNames);
        this.geneNames = Utils.unmodifiableSet(geneNames);
        this.proteomes = Utils.unmodifiableSet(proteomes);
        this.extraAttributes = Objects.isNull(extraAttributes) ? Map.of() : extraAttributes;
    }

    @Override
    public String getUniParcId() {
        return uniParcId;
    }

    @Override
    public int getCrossReferenceCount() {
        return crossReferenceCount;
    }

    @Override
    public List<CommonOrganism> getCommonTaxons() {
        return commonTaxons;
    }

    @Override
    public Set<String> getUniProtKBAccessions() {
        return uniProtKBAccessions;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public List<SequenceFeature> getSequenceFeatures() {
        return sequenceFeatures;
    }

    @Override
    public LocalDate getOldestCrossRefCreated() {
        return oldestCrossRefCreated;
    }

    @Override
    public LocalDate getMostRecentCrossRefUpdated() {
        return mostRecentCrossRefUpdated;
    }

    @Override
    public Set<Organism> getOrganisms() {
        return organisms;
    }

    @Override
    public Set<String> getProteinNames() {
        return proteinNames;
    }

    @Override
    public Set<String> getGeneNames() {
        return geneNames;
    }

    @Override
    public Set<Proteome> getProteomes() {
        return proteomes;
    }

    @Override
    public Map<String, Object> getExtraAttributes() {
        return this.extraAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniParcEntryLightImpl that = (UniParcEntryLightImpl) o;
        return Objects.equals(getUniParcId(), that.getUniParcId()) && Objects.equals(getCrossReferenceCount(),
                that.getCrossReferenceCount()) && Objects.equals(getCommonTaxons(), that.getCommonTaxons()) &&
                Objects.equals(getUniProtKBAccessions(), that.getUniProtKBAccessions()) &&
                Objects.equals(getSequence(), that.getSequence()) &&
                Objects.equals(getSequenceFeatures(), that.getSequenceFeatures()) &&
                Objects.equals(getOldestCrossRefCreated(), that.getOldestCrossRefCreated()) &&
                Objects.equals(getMostRecentCrossRefUpdated(), that.getMostRecentCrossRefUpdated()) &&
                Objects.equals(getOrganisms(), that.getOrganisms()) &&
                Objects.equals(getProteinNames(), that.getProteinNames()) &&
                Objects.equals(getGeneNames(), that.getGeneNames()) &&
                Objects.equals(getProteomes(), that.getProteomes()) &&
                Objects.equals(getExtraAttributes(), that.getExtraAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniParcId(), getCrossReferenceCount(), getCommonTaxons(), getUniProtKBAccessions(),
                 getSequence(), getSequenceFeatures(), getOldestCrossRefCreated(),
                getMostRecentCrossRefUpdated(), getOrganisms(), getProteinNames(), getGeneNames(), getProteomes(),
                getExtraAttributes());
    }
}
