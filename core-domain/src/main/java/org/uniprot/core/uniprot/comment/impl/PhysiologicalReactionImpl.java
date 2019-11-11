package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class PhysiologicalReactionImpl implements PhysiologicalReaction {
    private static final long serialVersionUID = -3913438195830117987L;
    private PhysiologicalDirectionType directionType;
    private DBCrossReference<ReactionReferenceType> reactionReference;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    PhysiologicalReactionImpl() {
        this.evidences = Collections.emptyList();
    }

    public PhysiologicalReactionImpl(
            PhysiologicalDirectionType directionType,
            DBCrossReference<ReactionReferenceType> reactionReference,
            List<Evidence> evidences) {
        this.directionType = directionType;
        this.reactionReference = reactionReference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
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
    public boolean hasDirectionType() {
        return this.directionType != null;
    }

    @Override
    public boolean hasReactionReference() {
        return this.reactionReference != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysiologicalReactionImpl that = (PhysiologicalReactionImpl) o;
        return directionType == that.directionType
                && Objects.equals(reactionReference, that.reactionReference)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directionType, reactionReference, evidences);
    }
}
