package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.PhysiologicalReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class PhysiologicalReactionBuilder implements Builder2<PhysiologicalReactionBuilder, PhysiologicalReaction> {
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
        this.evidences.addAll(evidences);
        return this;
    }

    public PhysiologicalReactionBuilder addEvidences(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    public PhysiologicalReactionImpl build() {
        return new PhysiologicalReactionImpl(this);
    }

    @Override
    public PhysiologicalReactionBuilder from(PhysiologicalReaction instance) {
        return new PhysiologicalReactionBuilder()
                .evidences(instance.getEvidences())
                .directionType(instance.getDirectionType())
                .reactionReference(instance.getReactionReference());
    }

    public PhysiologicalDirectionType getDirectionType() {
        return directionType;
    }

    public DBCrossReference<ReactionReferenceType> getReactionReference() {
        return reactionReference;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
