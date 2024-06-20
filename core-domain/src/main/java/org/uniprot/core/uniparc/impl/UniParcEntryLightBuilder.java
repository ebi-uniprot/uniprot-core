package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UniParcEntryLightBuilder implements Builder<UniParcEntryLight> {
    private UniParcId uniParcId;
    private List<String> uniParcCrossReferences = new ArrayList<>();
    private List<Pair<Integer, String>> commonTaxons = new ArrayList<>();
    private List<String> uniProtKBAccessions = new ArrayList<>();
    private String uniProtExclusionReason;

    private Sequence sequence;

    private List<SequenceFeature> sequenceFeatures = new ArrayList<>();

    private LocalDate oldestCrossRefCreated;

    private LocalDate mostRecentCrossRefUpdated;

    private List<Pair<Integer, String>> organisms = new ArrayList<>();

    private List<String> proteinNames = new ArrayList<>();

    private List<String> geneNames = new ArrayList<>();

    private List<String> proteomeIds = new ArrayList<>();

    @Nonnull
    @Override
    public UniParcEntryLight build() {
        return new UniParcEntryLightImpl(uniParcId, uniParcCrossReferences, commonTaxons, uniProtKBAccessions,
                uniProtExclusionReason, sequence, sequenceFeatures, oldestCrossRefCreated,
                mostRecentCrossRefUpdated, organisms, proteinNames, geneNames, proteomeIds);
    }


    public @Nonnull UniParcEntryLightBuilder uniParcId(String uniParcId) {
        return uniParcId(new UniParcIdBuilder(uniParcId).build());
    }

    public  @Nonnull UniParcEntryLightBuilder uniParcId(UniParcId uniParcId) {
        this.uniParcId = uniParcId;
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniParcCrossReferencesSet(List<String> uniParcCrossReferences){
        this.uniParcCrossReferences = Utils.modifiableList(uniParcCrossReferences);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniParcCrossReferencesAdd(String uniParcCrossReferenceId){
        Utils.addOrIgnoreNull(uniParcCrossReferenceId, this.uniParcCrossReferences);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder commonTaxonsSet(List<Pair<Integer, String>> commonTaxons){
        this.commonTaxons = Utils.modifiableList(commonTaxons);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder commonTaxonsAdd(Pair<Integer, String> commonTaxon){
        Utils.addOrIgnoreNull(commonTaxon, this.commonTaxons);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniProtKBAccessionsSet(List<String> uniProtKBAccessions){
        this.uniProtKBAccessions = Utils.modifiableList(uniProtKBAccessions);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniProtKBAccessionsAdd(String uniProtKBAccession){
         Utils.addOrIgnoreNull(uniProtKBAccession, this.uniProtKBAccessions);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniProtExclusionReason(String uniProtExclusionReason){
        this.uniProtExclusionReason = uniProtExclusionReason;
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder sequenceFeaturesSet(List<SequenceFeature> sequenceFeatures) {
        this.sequenceFeatures = Utils.modifiableList(sequenceFeatures);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder sequenceFeaturesAdd(SequenceFeature sequenceFeature) {
        Utils.addOrIgnoreNull(sequenceFeature, this.sequenceFeatures);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder oldestCrossRefCreated(LocalDate oldestCrossRefCreated) {
        this.oldestCrossRefCreated = oldestCrossRefCreated;
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder mostRecentCrossRefUpdated(LocalDate mostRecentCrossRefUpdated) {
        this.mostRecentCrossRefUpdated = mostRecentCrossRefUpdated;
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder organismsSet(List<Pair<Integer, String>> organisms) {
        this.organisms = Utils.modifiableList(organisms);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder organismsAdd(Pair<Integer, String> organism) {
        Utils.addOrIgnoreNull(organism, this.organisms);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteinNamesSet(List<String> proteinNames) {
        this.proteinNames = Utils.modifiableList(proteinNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteinNamesAdd(String proteinName) {
        Utils.addOrIgnoreNull(proteinName, this.proteinNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder geneNamesSet(List<String> geneNames) {
        this.geneNames = Utils.modifiableList(geneNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder geneNamesAdd(String geneName) {
        Utils.addOrIgnoreNull(geneName, this.geneNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteomeIdsSet(List<String> proteomeIds) {
        this.proteomeIds = Utils.modifiableList(proteomeIds);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteomeIdsAdd(String proteomeId) {
        Utils.addOrIgnoreNull(proteomeId, this.proteomeIds);
        return this;
    }

    public static @Nonnull UniParcEntryLightBuilder from(UniParcEntryLight uniParcEntryLight){
        return new UniParcEntryLightBuilder().uniParcId(uniParcEntryLight.getUniParcId())
                .uniParcCrossReferencesSet(uniParcEntryLight.getUniParcCrossReferences())
                .commonTaxonsSet(uniParcEntryLight.getCommonTaxons())
                .uniProtKBAccessionsSet(uniParcEntryLight.getUniProtKBAccessions())
                .uniProtExclusionReason(uniParcEntryLight.getUniProtExclusionReason())
                .sequence(uniParcEntryLight.getSequence())
                .sequenceFeaturesSet(uniParcEntryLight.getSequenceFeatures())
                .oldestCrossRefCreated(uniParcEntryLight.getOldestCrossRefCreated())
                .mostRecentCrossRefUpdated(uniParcEntryLight.getMostRecentCrossRefUpdated())
                .organismsSet(uniParcEntryLight.getOrganisms())
                .proteinNamesSet(uniParcEntryLight.getProteinNames())
                .geneNamesSet(uniParcEntryLight.getGeneNames())
                .proteomeIdsSet(uniParcEntryLight.getProteomeIds());
    }
}
