package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class APIsoformImpl implements APIsoform {
    private IsoformName name;
    private List<IsoformName> synonyms;
    private Note note;
    private List<IsoformId> isoformIds;
    private List<String> sequenceIds;
    private IsoformSequenceStatus isoformSequenceStatus;

    private APIsoformImpl() {
        isoformIds = Collections.emptyList();
        sequenceIds = Collections.emptyList();
        synonyms = Collections.emptyList();
    }

    public APIsoformImpl(IsoformName name,
                         List<IsoformName> synonyms,
                         Note note,
                         List<IsoformId> isoformIds,
                         List<String> sequenceIds,
                         IsoformSequenceStatus isoformSequenceStatus) {
        this.name = name;
        this.synonyms = Utils.nonNullUnmodifiableList(synonyms);
        this.note = note;
        this.isoformIds = Utils.nonNullUnmodifiableList(isoformIds);
        this.sequenceIds = Utils.nonNullUnmodifiableList(sequenceIds);

        if (isoformSequenceStatus == null) {
            this.isoformSequenceStatus = IsoformSequenceStatus.DESCRIBED;
        } else
            this.isoformSequenceStatus = isoformSequenceStatus;
    }

    @Override
    public IsoformName getName() {
        return name;
    }

    @Override
    public List<IsoformName> getSynonyms() {
        return synonyms;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public List<IsoformId> getIsoformIds() {
        return isoformIds;
    }

    @Override
    public List<String> getSequenceIds() {
        return sequenceIds;
    }

    @Override
    public IsoformSequenceStatus getIsoformSequenceStatus() {
        return isoformSequenceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIsoformImpl apIsoform = (APIsoformImpl) o;
        return Objects.equals(name, apIsoform.name) &&
                Objects.equals(synonyms, apIsoform.synonyms) &&
                Objects.equals(note, apIsoform.note) &&
                Objects.equals(isoformIds, apIsoform.isoformIds) &&
                Objects.equals(sequenceIds, apIsoform.sequenceIds) &&
                isoformSequenceStatus == apIsoform.isoformSequenceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, synonyms, note, isoformIds, sequenceIds, isoformSequenceStatus);
    }

    public static class IsoformNameImpl extends EvidencedValueImpl implements IsoformName {
        private IsoformNameImpl() {
            super("", Collections.emptyList());
        }

        public IsoformNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }

    public static class IsoformIdImpl extends ValueImpl implements IsoformId {
        private IsoformIdImpl() {
            super("");
        }

        public IsoformIdImpl(String value) {
            super(value);
        }
    }
}
