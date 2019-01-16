package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl;

import java.util.ArrayList;
import java.util.List;

public class CatalyticActivityCommentBuilder implements CommentBuilder<CatalyticActivityCommentBuilder, CatalyticActivityComment> {
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions = new ArrayList<>();

//    public static CatalyticActivityCommentBuilder newInstance() {
//        return new CatalyticActivityCommentBuilder();
//    }

//    public static Reaction createReaction(String name,
//                                          List<DBCrossReference<ReactionReferenceType>> reactionReferences,
//                                          ECNumber ecNumber, List<Evidence> evidences) {
//        return new ReactionImpl(name, reactionReferences, ecNumber, evidences);
//
//    }
//
//    public static PhysiologicalReaction createPhysiologicalReaction(PhysiologicalDirectionType directionType,
//                                                                    DBCrossReference<ReactionReferenceType> reactionReference,
//                                                                    List<Evidence> evidences) {
//
//        return new PhysiologicalReactionImpl(directionType, reactionReference, evidences);
//    }

    @Override
    public CatalyticActivityComment build() {
        return new CatalyticActivityCommentImpl(reaction, physiologicalReactions);
    }

    @Override
    public CatalyticActivityCommentBuilder from(CatalyticActivityComment instance) {
        return new CatalyticActivityCommentBuilder()
                .physiologicalReactions(instance.getPhysiologicalReactions())
                .reaction(instance.getReaction());
    }

    public CatalyticActivityCommentBuilder reaction(Reaction reaction) {
        this.reaction = reaction;
        return this;
    }

    public CatalyticActivityCommentBuilder physiologicalReactions(List<PhysiologicalReaction> physiologicalReactions) {
        this.physiologicalReactions.addAll(physiologicalReactions);
        return this;
    }

    public CatalyticActivityCommentBuilder addPhysiologicalReaction(PhysiologicalReaction physiologicalReaction) {
        this.physiologicalReactions.add(physiologicalReaction);
        return this;
    }
}
