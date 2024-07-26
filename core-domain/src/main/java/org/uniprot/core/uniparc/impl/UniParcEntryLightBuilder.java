package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.CommonOrganism;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniParcEntryLightBuilder implements Builder<UniParcEntryLight> {
    private String uniParcId;
    private List<String> uniParcCrossReferences = new ArrayList<>();
    private List<CommonOrganism> commonTaxons = new ArrayList<>();
    private LinkedHashSet<String> uniProtKBAccessions = new LinkedHashSet<>();

    private Sequence sequence;

    private List<SequenceFeature> sequenceFeatures = new ArrayList<>();

    private LocalDate oldestCrossRefCreated;

    private LocalDate mostRecentCrossRefUpdated;

    private Set<Organism> organisms = new LinkedHashSet<>();

    private Set<String> proteinNames = new LinkedHashSet<>();

    private Set<String> geneNames = new LinkedHashSet<>();

    private Set<String> proteomes = new LinkedHashSet<>();

    @Nonnull
    @Override
    public UniParcEntryLight build() {
        return new UniParcEntryLightImpl(uniParcId, uniParcCrossReferences, commonTaxons, uniProtKBAccessions,
                sequence, sequenceFeatures, oldestCrossRefCreated,
                mostRecentCrossRefUpdated, (LinkedHashSet<Organism>) organisms, (LinkedHashSet<String>) proteinNames, (LinkedHashSet<String>) geneNames, (LinkedHashSet<String>) proteomes);
    }

    public  @Nonnull UniParcEntryLightBuilder uniParcId(String uniParcId) {
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

    public @Nonnull UniParcEntryLightBuilder commonTaxonsSet(List<CommonOrganism> commonTaxons){
        this.commonTaxons = Utils.modifiableList(commonTaxons);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder commonTaxonsAdd(CommonOrganism commonTaxon){
        Utils.addOrIgnoreNull(commonTaxon, this.commonTaxons);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniProtKBAccessionsSet(LinkedHashSet<String> uniProtKBAccessions){
        this.uniProtKBAccessions = Utils.modifiableLinkedHashSet(uniProtKBAccessions);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder uniProtKBAccessionsAdd(String uniProtKBAccession){
         Utils.addOrIgnoreNull(uniProtKBAccession, this.uniProtKBAccessions);
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

    public @Nonnull UniParcEntryLightBuilder organismsSet(LinkedHashSet<Organism> organisms) {
        this.organisms = Utils.modifiableLinkedHashSet(organisms);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder organismsAdd(Organism organism) {
        Utils.addOrIgnoreNull(organism, this.organisms);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteinNamesSet(LinkedHashSet<String> proteinNames) {
        this.proteinNames = Utils.modifiableLinkedHashSet(proteinNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteinNamesAdd(String proteinName) {
        Utils.addOrIgnoreNull(proteinName, this.proteinNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder geneNamesSet(LinkedHashSet<String> geneNames) {
        this.geneNames = Utils.modifiableLinkedHashSet(geneNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder geneNamesAdd(String geneName) {
        Utils.addOrIgnoreNull(geneName, this.geneNames);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteomesSet(LinkedHashSet<String> proteomes) {
        this.proteomes = Utils.modifiableLinkedHashSet(proteomes);
        return this;
    }

    public @Nonnull UniParcEntryLightBuilder proteomesAdd(String proteome) {
        Utils.addOrIgnoreNull(proteome, this.proteomes);
        return this;
    }

    public static @Nonnull UniParcEntryLightBuilder from(UniParcEntryLight uniParcEntryLight){
        LinkedHashSet<String> uniprotKbAccessions = new LinkedHashSet<>(uniParcEntryLight.getUniProtKBAccessions());
        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(uniParcEntryLight.getOrganisms());
        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(uniParcEntryLight.getProteinNames());
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(uniParcEntryLight.getGeneNames());
        LinkedHashSet<String> proteomes = new LinkedHashSet<>(uniParcEntryLight.getProteomes());
        return new UniParcEntryLightBuilder().uniParcId(uniParcEntryLight.getUniParcId())
                .uniParcCrossReferencesSet(uniParcEntryLight.getUniParcCrossReferences())
                .commonTaxonsSet(uniParcEntryLight.getCommonTaxons())
                .uniProtKBAccessionsSet(uniprotKbAccessions)
                .sequence(uniParcEntryLight.getSequence())
                .sequenceFeaturesSet(uniParcEntryLight.getSequenceFeatures())
                .oldestCrossRefCreated(uniParcEntryLight.getOldestCrossRefCreated())
                .mostRecentCrossRefUpdated(uniParcEntryLight.getMostRecentCrossRefUpdated())
                .organismsSet(organisms)
                .proteinNamesSet(proteinNames)
                .geneNamesSet(geneNames)
                .proteomesSet(proteomes);
    }
}
