package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.util.Utils;

public class APIsoformImpl implements APIsoform {
    private static final long serialVersionUID = -6908166238877018418L;
    private IsoformName name;
    private List<IsoformName> synonyms;
    private Note note;
    private List<IsoformId> isoformIds;
    private List<String> sequenceIds;
    private IsoformSequenceStatus isoformSequenceStatus;

    // no arg constructor for JSON deserialization
    APIsoformImpl() {
        isoformIds = Collections.emptyList();
        sequenceIds = Collections.emptyList();
        synonyms = Collections.emptyList();
    }

    APIsoformImpl(
            IsoformName name,
            List<IsoformName> synonyms,
            Note note,
            List<IsoformId> isoformIds,
            List<String> sequenceIds,
            IsoformSequenceStatus isoformSequenceStatus) {
        this.name = name;
        this.synonyms = Utils.unmodifiableList(synonyms);
        this.note = note;
        this.isoformIds = Utils.unmodifiableList(isoformIds);
        this.sequenceIds = Utils.unmodifiableList(sequenceIds);

        if (isoformSequenceStatus == null) {
            this.isoformSequenceStatus = IsoformSequenceStatus.DESCRIBED;
        } else this.isoformSequenceStatus = isoformSequenceStatus;
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
    public boolean hasName() {
        return this.name != null;
    }

    @Override
    public boolean hasSynonyms() {
        return Utils.notNullNotEmpty(this.synonyms);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasIsoformIds() {
        return Utils.notNullNotEmpty(this.isoformIds);
    }

    @Override
    public boolean hasSequenceIds() {
        return Utils.notNullNotEmpty(this.sequenceIds);
    }

    @Override
    public boolean hasIsoformSequenceStatus() {
        return this.isoformSequenceStatus != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIsoformImpl apIsoform = (APIsoformImpl) o;
        return Objects.equals(name, apIsoform.name)
                && Objects.equals(synonyms, apIsoform.synonyms)
                && Objects.equals(note, apIsoform.note)
                && Objects.equals(isoformIds, apIsoform.isoformIds)
                && Objects.equals(sequenceIds, apIsoform.sequenceIds)
                && isoformSequenceStatus == apIsoform.isoformSequenceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, synonyms, note, isoformIds, sequenceIds, isoformSequenceStatus);
    }

    public static class IsoformNameImpl extends EvidencedValueImpl implements IsoformName {
        /** */
        private static final long serialVersionUID = 8473412391754613382L;

        // no arg constructor for JSON deserialization
        IsoformNameImpl() {
            super("", Collections.emptyList());
        }

        IsoformNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }

    public static class IsoformIdImpl extends ValueImpl implements IsoformId {
        /** */
        private static final long serialVersionUID = -8782819385757982941L;

        // no arg constructor for JSON deserialization
        IsoformIdImpl() {
            super("");
        }

        IsoformIdImpl(String value) {
            super(value);
        }
    }
}
