package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.impl.PhysiologicalReactionImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class PhysiologicalReactionBuilder implements Builder<PhysiologicalReactionBuilder, PhysiologicalReaction> {
    private PhysiologicalDirectionType directionType;
    private DBCrossReference<ReactionReferenceType> reactionReference;
    private List<Evidence> evidences = new ArrayList<>();

    public PhysiologicalReactionBuilder directionType(PhysiologicalDirectionType directionType) {
        this.directionType = directionType;
        return this;
    }

    public PhysiologicalReactionBuilder reactionReference(DBCrossReference<ReactionReferenceType> reactionReference) {
        this.reactionReference = reactionReference;
        return this;
    }

    public PhysiologicalReactionBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public PhysiologicalReactionBuilder addEvidences(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }

    public PhysiologicalReactionImpl build() {
        return new PhysiologicalReactionImpl(directionType, reactionReference, evidences);
    }

    @Override
    public PhysiologicalReactionBuilder from(PhysiologicalReaction instance) {
        evidences.clear();
        return this
                .evidences(instance.getEvidences())
                .directionType(instance.getDirectionType())
                .reactionReference(instance.getReactionReference());
    }
}
