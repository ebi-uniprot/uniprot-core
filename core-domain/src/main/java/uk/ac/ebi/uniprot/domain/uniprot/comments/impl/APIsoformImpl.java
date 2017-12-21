package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;

public class APIsoformImpl implements APIsoform {
    public static IsoformSynonym createIsoformSynonym(String value, List<Evidence> evidences){
        return new IsoformSynonymImpl(value, evidences);
    }
    public static IsoformName createIsoformName(String value, List<Evidence> evidences){
        return new IsoformNameImpl(value, evidences);
    }
    private final IsoformName name;
    private final List<IsoformSynonym> synonyms;
    private final CommentNote note;
    private final List<IsoformId> isoformIds;
    private final List<String> sequenceIds;
    private final IsoformSequenceStatus isoformSequenceStatus;
    public APIsoformImpl(IsoformName name, List<IsoformSynonym> synonyms, 
            CommentNote note, List<IsoformId> isoformIds,
            List<String> sequenceIds, IsoformSequenceStatus isoformSequenceStatus ){
        this.name =name;
        if((synonyms ==null) || synonyms.isEmpty()){
            this.synonyms = Collections.emptyList();
        }else{
            this.synonyms =Collections.unmodifiableList(synonyms);
        }
        this.note = note;
        if((isoformIds ==null) || isoformIds.isEmpty()){
            this.isoformIds = Collections.emptyList();
        }else{
            this.isoformIds =Collections.unmodifiableList(isoformIds);
        }
        if((sequenceIds ==null) || sequenceIds.isEmpty()){
            this.sequenceIds = Collections.emptyList();
        }else{
            this.sequenceIds =Collections.unmodifiableList(sequenceIds);
        }
        this.isoformSequenceStatus = isoformSequenceStatus;
    }
    @Override
    public IsoformName getName() {
       return name;
    }

    @Override
    public boolean hasName() {
        return name !=null;
    }

    @Override
    public List<IsoformSynonym> getSynonyms() {
        return synonyms;
    }

    @Override
    public CommentNote getNote() {
       return note;
    }

    @Override
    public boolean hasNote() {
       return note !=null;
    }

    @Override
    public List<IsoformId> getIds() {
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
    static class IsoformSynonymImpl extends EvidencedValueImpl implements IsoformSynonym{
        public IsoformSynonymImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }
    static class IsoformNameImpl extends EvidencedValueImpl implements IsoformName{
        public IsoformNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }
}
