package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.PhysiologicalReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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
