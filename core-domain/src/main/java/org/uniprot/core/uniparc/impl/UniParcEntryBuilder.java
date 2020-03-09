package org.uniprot.core.uniparc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcEntryBuilder implements Builder<UniParcEntry> {
    private UniParcId uniParcId;
    private List<UniParcCrossReference> uniParcCrossReferences = new ArrayList<>();
    private Sequence sequence;
    private String uniprotExclusionReason;
    private List<SequenceFeature> sequenceFeatures = new ArrayList<>();
    private List<Taxonomy> taxonomies = new ArrayList<>();

    @Override
    public @Nonnull UniParcEntry build() {
        return new UniParcEntryImpl(
                uniParcId,
                uniParcCrossReferences,
                sequence,
                sequenceFeatures,
                taxonomies,
                uniprotExclusionReason);
    }

    public @Nonnull UniParcEntryBuilder uniParcId(String uniParcId) {
        return uniParcId(new UniParcIdBuilder(uniParcId).build());
    }

    public @Nonnull UniParcEntryBuilder uniParcId(UniParcId uniParcId) {
        this.uniParcId = uniParcId;
        return this;
    }

    public @Nonnull UniParcEntryBuilder uniParcCrossReferencesSet(
            List<UniParcCrossReference> uniParcCrossReferences) {
        this.uniParcCrossReferences = Utils.modifiableList(uniParcCrossReferences);
        return this;
    }

    public @Nonnull UniParcEntryBuilder uniParcCrossReferencesAdd(
            UniParcCrossReference uniParcCrossReference) {
        Utils.addOrIgnoreNull(uniParcCrossReference, uniParcCrossReferences);
        return this;
    }

    public @Nonnull UniParcEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniParcEntryBuilder uniprotExclusionReason(String uniprotExclusionReason) {
        this.uniprotExclusionReason = uniprotExclusionReason;
        return this;
    }

    public @Nonnull UniParcEntryBuilder sequenceFeaturesSet(
            List<SequenceFeature> sequenceFeatures) {
        this.sequenceFeatures = Utils.modifiableList(sequenceFeatures);
        return this;
    }

    public @Nonnull UniParcEntryBuilder sequenceFeaturesAdd(SequenceFeature sequenceFeature) {
        Utils.addOrIgnoreNull(sequenceFeature, sequenceFeatures);
        return this;
    }

    public @Nonnull UniParcEntryBuilder taxonomiesSet(List<Taxonomy> taxonomies) {
        this.taxonomies = Utils.modifiableList(taxonomies);
        return this;
    }

    public @Nonnull UniParcEntryBuilder taxonomiesAdd(Taxonomy taxonomy) {
        Utils.addOrIgnoreNull(taxonomy, taxonomies);
        return this;
    }

    public static @Nonnull UniParcEntryBuilder from(@Nonnull UniParcEntry instance) {
        return new UniParcEntryBuilder()
                .uniParcId(instance.getUniParcId())
                .uniParcCrossReferencesSet(instance.getUniParcCrossReferences())
                .sequence(instance.getSequence())
                .uniprotExclusionReason(instance.getUniProtExclusionReason())
                .sequenceFeaturesSet(instance.getSequenceFeatures())
                .taxonomiesSet(instance.getTaxonomies());
    }
}
