package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.impl.CatalyticActivityCommentImpl;

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
        this.physiologicalReactions = modifiableList(physiologicalReactions);
        return this;
    }

    public CatalyticActivityCommentBuilder addPhysiologicalReaction(PhysiologicalReaction physiologicalReaction) {
        addOrIgnoreNull(physiologicalReaction, this.physiologicalReactions);
        return this;
    }
}
