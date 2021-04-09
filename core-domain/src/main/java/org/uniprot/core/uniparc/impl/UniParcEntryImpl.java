package org.uniprot.core.uniparc.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class UniParcEntryImpl implements UniParcEntry {

    /** */
    private static final long serialVersionUID = 1558006779501834241L;

    private final UniParcId uniParcId;
    private final List<UniParcCrossReference> uniParcCrossReferences;
    private final Sequence sequence;
    private final String uniprotExclusionReason;
    private final List<SequenceFeature> sequenceFeatures;
    private LocalDate oldestCrossRefCreated;
    private LocalDate mostRecentCrossRefUpdated;

    UniParcEntryImpl() {
        this(null, null, null, null, null);
    }

    UniParcEntryImpl(
            UniParcId uniParcId,
            List<UniParcCrossReference> uniParcCrossReferences,
            Sequence sequence,
            List<SequenceFeature> sequenceFeatures,
            String uniprotExclusionReason) {
        super();
        this.uniParcId = uniParcId;
        this.uniParcCrossReferences = Utils.unmodifiableList(uniParcCrossReferences);
        this.sequence = sequence;
        this.sequenceFeatures = Utils.unmodifiableList(sequenceFeatures);
        this.uniprotExclusionReason = uniprotExclusionReason;
        this.oldestCrossRefCreated = getOldestCrossRefCreated();
        this.mostRecentCrossRefUpdated = getMostRecentCrossRefUpdated();
    }

    @Override
    public UniParcId getUniParcId() {
        return uniParcId;
    }

    @Override
    public List<UniParcCrossReference> getUniParcCrossReferences() {
        return uniParcCrossReferences;
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
    public LocalDate getOldestCrossRefCreated() {
        if (Objects.isNull(this.oldestCrossRefCreated)) {
            this.oldestCrossRefCreated =
                    getUniParcCrossReferences().stream()
                            .filter(xref -> xref.getCreated() != null)
                            .map(UniParcCrossReference::getCreated)
                            .min(Comparator.comparing(LocalDate::toEpochDay))
                            .orElse(null);
        }
        return this.oldestCrossRefCreated;
    }

    @Override
    public LocalDate getMostRecentCrossRefUpdated() {
        if (Objects.isNull(this.mostRecentCrossRefUpdated)) {
            this.mostRecentCrossRefUpdated =
                    getUniParcCrossReferences().stream()
                            .filter(xref -> xref.getLastUpdated() != null)
                            .map(UniParcCrossReference::getLastUpdated)
                            .max(Comparator.comparing(LocalDate::toEpochDay))
                            .orElse(null);
        }

        return this.mostRecentCrossRefUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniParcEntryImpl that = (UniParcEntryImpl) o;
        return Objects.equals(uniParcId, that.uniParcId)
                && Objects.equals(uniParcCrossReferences, that.uniParcCrossReferences)
                && Objects.equals(sequence, that.sequence)
                && Objects.equals(uniprotExclusionReason, that.uniprotExclusionReason)
                && Objects.equals(sequenceFeatures, that.sequenceFeatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniParcId,
                uniParcCrossReferences,
                sequence,
                uniprotExclusionReason,
                sequenceFeatures);
    }
}
