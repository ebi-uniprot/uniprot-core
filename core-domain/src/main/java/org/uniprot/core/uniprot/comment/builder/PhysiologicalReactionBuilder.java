package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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
public final class PhysiologicalReactionBuilder
        implements Builder<PhysiologicalReactionBuilder, PhysiologicalReaction> {
    private PhysiologicalDirectionType directionType;
    private DBCrossReference<ReactionReferenceType> reactionReference;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull PhysiologicalReactionBuilder directionType(
            PhysiologicalDirectionType directionType) {
        this.directionType = directionType;
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder reactionReference(
            DBCrossReference<ReactionReferenceType> reactionReference) {
        this.reactionReference = reactionReference;
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull PhysiologicalReactionImpl build() {
        return new PhysiologicalReactionImpl(directionType, reactionReference, evidences);
    }

    public static @Nonnull PhysiologicalReactionBuilder from(@Nonnull PhysiologicalReaction instance) {
        return new PhysiologicalReactionBuilder().evidences(instance.getEvidences())
                .directionType(instance.getDirectionType())
                .reactionReference(instance.getReactionReference());
    }
}
