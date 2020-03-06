package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.uniprot.comment.impl.PhysiologicalReactionImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class PhysiologicalReactionBuilder implements Builder<PhysiologicalReaction> {
    private PhysiologicalDirectionType directionType;
    private CrossReference<ReactionDatabase> reactionCrossReference;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull PhysiologicalReactionBuilder directionType(
            PhysiologicalDirectionType directionType) {
        this.directionType = directionType;
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder reactionCrossReference(
            CrossReference<ReactionDatabase> reactionReference) {
        this.reactionCrossReference = reactionReference;
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull PhysiologicalReactionBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull PhysiologicalReactionImpl build() {
        return new PhysiologicalReactionImpl(directionType, reactionCrossReference, evidences);
    }

    public static @Nonnull PhysiologicalReactionBuilder from(
            @Nonnull PhysiologicalReaction instance) {
        return new PhysiologicalReactionBuilder()
                .evidencesSet(instance.getEvidences())
                .directionType(instance.getDirectionType())
                .reactionCrossReference(instance.getReactionCrossReference());
    }
}
