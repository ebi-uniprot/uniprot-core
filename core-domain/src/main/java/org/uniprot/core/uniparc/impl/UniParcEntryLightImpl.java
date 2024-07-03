package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UniParcEntryLightImpl implements UniParcEntryLight {

    private static final long serialVersionUID = -6454735710941406443L;
    private final String uniParcId;
    private final List<String> uniParcCrossReferences;
    private final List<Pair<String, String>> commonTaxons;
    private final Set<String> uniProtKBAccessions;

    private final  Sequence sequence;

    private final  List<SequenceFeature> sequenceFeatures;

    private final LocalDate oldestCrossRefCreated;

    private final LocalDate mostRecentCrossRefUpdated;

    private final List<Pair<Integer, String>> organisms;

    private final List<String> proteinNames;

    private final List<String> geneNames;

    private final List<String> proteomeIds;

    UniParcEntryLightImpl() {
        this(null, null, null, null, null, null, null, null);
    }

    UniParcEntryLightImpl(String uniParcId, List<String> uniParcCrossReferences,
                                 List<Pair<String, String>> commonTaxons, Set<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated) {
        this(uniParcId, uniParcCrossReferences, commonTaxons, uniProtKBAccessions,
                sequence, sequenceFeatures, oldestCrossRefCreated, mostRecentCrossRefUpdated,
                null, null, null, null);
    }
    UniParcEntryLightImpl(String uniParcId, List<String> uniParcCrossReferences,
                                 List<Pair<String, String>> commonTaxons, Set<String> uniProtKBAccessions,
                                  Sequence sequence, List<SequenceFeature> sequenceFeatures,
                                 LocalDate oldestCrossRefCreated, LocalDate mostRecentCrossRefUpdated,
                                 List<Pair<Integer, String>> organisms, List<String> proteinNames, List<String> geneNames,
                                 List<String> proteomeIds) {
        this.uniParcId = uniParcId;
        this.uniParcCrossReferences = Utils.unmodifiableList(uniParcCrossReferences);
        this.commonTaxons = Utils.unmodifiableList(commonTaxons);
        this.uniProtKBAccessions = Utils.unmodifiableSet(uniProtKBAccessions);
        this.sequence = sequence;
        this.sequenceFeatures = Utils.unmodifiableList(sequenceFeatures);
        this.oldestCrossRefCreated = oldestCrossRefCreated;
        this.mostRecentCrossRefUpdated = mostRecentCrossRefUpdated;
        this.organisms = Utils.unmodifiableList(organisms);
        this.proteinNames = Utils.unmodifiableList(proteinNames);
        this.geneNames = Utils.unmodifiableList(geneNames);
        this.proteomeIds = Utils.unmodifiableList(proteomeIds);
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
    public List<Pair<String, String>> getCommonTaxons() {
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
    public List<Pair<Integer, String>> getOrganisms() {
        return organisms;
    }

    @Override
    public List<String> getProteinNames() {
        return proteinNames;
    }

    @Override
    public List<String> getGeneNames() {
        return geneNames;
    }

    @Override
    public List<String> getProteomeIds() {
        return proteomeIds;
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
                Objects.equals(getProteomeIds(), that.getProteomeIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniParcId(), getUniParcCrossReferences(), getCommonTaxons(), getUniProtKBAccessions(),
                 getSequence(), getSequenceFeatures(), getOldestCrossRefCreated(),
                getMostRecentCrossRefUpdated(), getOrganisms(), getProteinNames(), getGeneNames(), getProteomeIds());
    }
}
