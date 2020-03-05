package org.uniprot.core.uniparc.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class UniParcEntryImpl implements UniParcEntry {

    /** */
    private static final long serialVersionUID = 1558006779501834241L;

    private UniParcId uniParcId;
    private List<UniParcCrossReference> databaseCrossReferences;
    private Sequence sequence;
    private String uniprotExclusionReason;
    private List<SequenceFeature> sequenceFeatures;
    private List<Taxonomy> taxonomies;

    protected UniParcEntryImpl() {
        this.databaseCrossReferences = Collections.emptyList();
        this.sequenceFeatures = Collections.emptyList();
        this.taxonomies = Collections.emptyList();
    }

    public UniParcEntryImpl(
            UniParcId uniParcId,
            List<UniParcCrossReference> databaseCrossReferences,
            Sequence sequence,
            List<SequenceFeature> sequenceFeatures,
            List<Taxonomy> taxonomies,
            String uniprotExclusionReason) {
        super();
        this.uniParcId = uniParcId;
        this.databaseCrossReferences = Utils.unmodifiableList(databaseCrossReferences);
        this.sequence = sequence;

        this.sequenceFeatures = Utils.unmodifiableList(sequenceFeatures);
        this.taxonomies = Utils.unmodifiableList(taxonomies);
        this.uniprotExclusionReason = uniprotExclusionReason;
    }

    @Override
    public UniParcId getUniParcId() {
        return uniParcId;
    }

    @Override
    public List<UniParcCrossReference> getDbXReferences() {
        return databaseCrossReferences;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public String getUniProtExclusionReason() {
        return uniprotExclusionReason;
    }

    @Override
    public List<SequenceFeature> getSequenceFeatures() {
        return sequenceFeatures;
    }

    @Override
    public List<Taxonomy> getTaxonomies() {
        if ((taxonomies == null) || taxonomies.isEmpty()) {
            taxonomies =
                    databaseCrossReferences.stream()
                            .flatMap(val -> val.getProperties().stream())
                            .filter(
                                    val ->
                                            val.getKey()
                                                    .equals(
                                                            UniParcCrossReference
                                                                    .PROPERTY_NCBI_TAXONOMY_ID))
                            .map(Property::getValue)
                            .distinct()
                            .map(this::convertTaxonomy)
                            .collect(Collectors.toList());
        }
        return taxonomies;
    }

    private Taxonomy convertTaxonomy(String taxId) {
        return new TaxonomyBuilder().taxonId(Long.parseLong(taxId)).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniParcEntryImpl that = (UniParcEntryImpl) o;
        return Objects.equals(uniParcId, that.uniParcId)
                && Objects.equals(databaseCrossReferences, that.databaseCrossReferences)
                && Objects.equals(sequence, that.sequence)
                && Objects.equals(uniprotExclusionReason, that.uniprotExclusionReason)
                && Objects.equals(sequenceFeatures, that.sequenceFeatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniParcId,
                databaseCrossReferences,
                sequence,
                uniprotExclusionReason,
                sequenceFeatures);
    }
}
