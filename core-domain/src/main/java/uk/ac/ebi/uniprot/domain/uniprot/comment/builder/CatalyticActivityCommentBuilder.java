package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

public class CatalyticActivityCommentBuilder implements CommentBuilder<CatalyticActivityCommentBuilder, CatalyticActivityComment> {
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions = new ArrayList<>();

    @Override
    public CatalyticActivityComment build() {
        return new CatalyticActivityCommentImpl(reaction, physiologicalReactions);
    }

    @Override
    public CatalyticActivityCommentBuilder from(CatalyticActivityComment instance) {
        physiologicalReactions.clear();
        return this
                .physiologicalReactions(instance.getPhysiologicalReactions())
                .reaction(instance.getReaction());
    }

    public CatalyticActivityCommentBuilder reaction(Reaction reaction) {
        this.reaction = reaction;
        return this;
    }

    public CatalyticActivityCommentBuilder physiologicalReactions(List<PhysiologicalReaction> physiologicalReactions) {
        nonNullAddAll(physiologicalReactions, this.physiologicalReactions);
        return this;
    }

    public CatalyticActivityCommentBuilder addPhysiologicalReaction(PhysiologicalReaction physiologicalReaction) {
        nonNullAdd(physiologicalReaction, this.physiologicalReactions);
        return this;
    }
}
