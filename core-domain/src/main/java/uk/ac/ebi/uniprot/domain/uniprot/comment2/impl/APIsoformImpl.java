package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;

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

    public APIsoformImpl(APCommentBuilder.APIsoformBuilder builder) {
        this.name = builder.getName();
        this.synonyms = Utils.unmodifierList(builder.getSynonyms());
        this.note = builder.getNote();
        this.isoformIds = Utils.unmodifierList(builder.getIsoformIds());
        this.sequenceIds = Utils.unmodifierList(builder.getSequenceIds());

        if (builder.getIsoformSequenceStatus() == null) {
            this.isoformSequenceStatus = IsoformSequenceStatus.DESCRIBED;
        } else
            this.isoformSequenceStatus = builder.getIsoformSequenceStatus();
    }

    public static IsoformName createIsoformName(String value, List<Evidence> evidences) {
        return new IsoformNameImpl(value, evidences);
    }

    public static IsoformId createIsoformId(String value) {
        return new IsoformIdImpl(value);
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isoformIds == null) ? 0 : isoformIds.hashCode());
        result = prime * result + ((isoformSequenceStatus == null) ? 0 : isoformSequenceStatus.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((sequenceIds == null) ? 0 : sequenceIds.hashCode());
        result = prime * result + ((synonyms == null) ? 0 : synonyms.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        APIsoformImpl other = (APIsoformImpl) obj;
        if (isoformIds == null) {
            if (other.isoformIds != null)
                return false;
        } else if (!isoformIds.equals(other.isoformIds))
            return false;
        if (isoformSequenceStatus != other.isoformSequenceStatus)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (sequenceIds == null) {
            if (other.sequenceIds != null)
                return false;
        } else if (!sequenceIds.equals(other.sequenceIds))
            return false;
        if (synonyms == null) {
            if (other.synonyms != null)
                return false;
        } else if (!synonyms.equals(other.synonyms))
            return false;
        return true;
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
