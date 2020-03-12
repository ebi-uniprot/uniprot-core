package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class PhysiologicalReactionImpl implements PhysiologicalReaction {
    private static final long serialVersionUID = -3913438195830117987L;
    private PhysiologicalDirectionType directionType;
    private CrossReference<ReactionDatabase> reactionCrossReference;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    PhysiologicalReactionImpl() {
        this.evidences = Collections.emptyList();
    }

    PhysiologicalReactionImpl(
            PhysiologicalDirectionType directionType,
            CrossReference<ReactionDatabase> reactionCrossReference,
            List<Evidence> evidences) {
        this.directionType = directionType;
        this.reactionCrossReference = reactionCrossReference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public PhysiologicalDirectionType getDirectionType() {
        return directionType;
    }

    @Override
    public CrossReference<ReactionDatabase> getReactionCrossReference() {
        return reactionCrossReference;
    }

    @Override
    public boolean hasDirectionType() {
        return this.directionType != null;
    }

    @Override
    public boolean hasReactionCrossReference() {
        return this.reactionCrossReference != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysiologicalReactionImpl that = (PhysiologicalReactionImpl) o;
        return directionType == that.directionType
                && Objects.equals(reactionCrossReference, that.reactionCrossReference)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directionType, reactionCrossReference, evidences);
    }
}
