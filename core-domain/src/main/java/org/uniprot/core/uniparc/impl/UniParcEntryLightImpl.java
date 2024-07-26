package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.CommonOrganism;
import org.uniprot.core.uniparc.Proteome;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UniParcEntryLightImpl implements UniParcEntryLight {

    private static final long serialVersionUID = -5488878159071674580L;
    private final String uniParcId;
    private final List<String> uniParcCrossReferences;
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

    UniParcEntryLightImpl() {
        this(null, null, null, null, null, null, null, null);
    }

    UniParcEntryLightImpl(String uniParcId, List<String> uniParcCrossReferences,
                          List<CommonOrganism> commonTaxons, LinkedHashSet<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated) {
        this(uniParcId, uniParcCrossReferences, commonTaxons, uniProtKBAccessions,
                sequence, sequenceFeatures, oldestCrossRefCreated, mostRecentCrossRefUpdated,
                null, null, null, null);
    }
    UniParcEntryLightImpl(String uniParcId, List<String> uniParcCrossReferences,
                          List<CommonOrganism> commonTaxons, LinkedHashSet<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated,
                          LinkedHashSet<Organism> organisms, LinkedHashSet<String> proteinNames, LinkedHashSet<String> geneNames,
                          LinkedHashSet<Proteome> proteomes) {
        this.uniParcId = uniParcId;
        this.uniParcCrossReferences = Utils.unmodifiableList(uniParcCrossReferences);
        this.commonTaxons = Utils.unmodifiableList(commonTaxons);
        this.uniProtKBAccessions = uniProtKBAccessions;
        this.sequence = sequence;
        this.sequenceFeatures = Utils.unmodifiableList(sequenceFeatures);
        this.oldestCrossRefCreated = oldestCrossRefCreated;
        this.mostRecentCrossRefUpdated = mostRecentCrossRefUpdated;
        this.organisms = Utils.unmodifiableSet(organisms);
        this.proteinNames = Utils.unmodifiableSet(proteinNames);
        this.geneNames = Utils.unmodifiableSet(geneNames);
        this.proteomes = Utils.unmodifiableSet(proteomes);
    }

    @Override
    public String getUniParcId() {
        return uniParcId;
    }

    @Override
    public List<String> getUniParcCrossReferences() {
        return uniParcCrossReferences;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniParcEntryLightImpl that = (UniParcEntryLightImpl) o;
        return Objects.equals(getUniParcId(), that.getUniParcId()) && Objects.equals(getUniParcCrossReferences(),
                that.getUniParcCrossReferences()) && Objects.equals(getCommonTaxons(), that.getCommonTaxons()) &&
                Objects.equals(getUniProtKBAccessions(), that.getUniProtKBAccessions()) &&
                Objects.equals(getSequence(), that.getSequence()) &&
                Objects.equals(getSequenceFeatures(), that.getSequenceFeatures()) &&
                Objects.equals(getOldestCrossRefCreated(), that.getOldestCrossRefCreated()) &&
                Objects.equals(getMostRecentCrossRefUpdated(), that.getMostRecentCrossRefUpdated()) &&
                Objects.equals(getOrganisms(), that.getOrganisms()) &&
                Objects.equals(getProteinNames(), that.getProteinNames()) &&
                Objects.equals(getGeneNames(), that.getGeneNames()) &&
                Objects.equals(getProteomes(), that.getProteomes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniParcId(), getUniParcCrossReferences(), getCommonTaxons(), getUniProtKBAccessions(),
                 getSequence(), getSequenceFeatures(), getOldestCrossRefCreated(),
                getMostRecentCrossRefUpdated(), getOrganisms(), getProteinNames(), getGeneNames(), getProteomes());
    }
}
