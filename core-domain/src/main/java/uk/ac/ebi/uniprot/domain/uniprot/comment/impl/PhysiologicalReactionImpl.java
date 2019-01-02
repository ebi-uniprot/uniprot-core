package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

public class PhysiologicalReactionImpl implements PhysiologicalReaction {
    private PhysiologicalDirectionType directionType;
    private DBCrossReference<ReactionReferenceType> reactionReference;
    private List<Evidence> evidences;

    private PhysiologicalReactionImpl() {
        this.evidences = Collections.emptyList();
    }

    public PhysiologicalReactionImpl(PhysiologicalDirectionType directionType,
                                     DBCrossReference<ReactionReferenceType> reactionReference,
                                     List<Evidence> evidences) {
        this.directionType = directionType;
        this.reactionReference = reactionReference;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public PhysiologicalDirectionType getDirectionType() {
        return directionType;
    }

    @Override
    public DBCrossReference<ReactionReferenceType> getReactionReference() {
        return reactionReference;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((directionType == null) ? 0 : directionType.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((reactionReference == null) ? 0 : reactionReference.hashCode());
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
        PhysiologicalReactionImpl other = (PhysiologicalReactionImpl) obj;
        if (directionType != other.directionType)
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (reactionReference == null) {
            if (other.reactionReference != null)
                return false;
        } else if (!reactionReference.equals(other.reactionReference))
            return false;
        return true;
    }

}
