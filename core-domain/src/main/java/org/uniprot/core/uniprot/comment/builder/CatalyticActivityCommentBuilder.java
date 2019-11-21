package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.impl.CatalyticActivityCommentImpl;

public class CatalyticActivityCommentBuilder
        implements CommentBuilder<CatalyticActivityCommentBuilder, CatalyticActivityComment> {
    private String molecule;
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions = new ArrayList<>();

    @Override
    public @Nonnull CatalyticActivityComment build() {
        return new CatalyticActivityCommentImpl(molecule, reaction, physiologicalReactions);
    }

    @Override
    public @Nonnull CatalyticActivityCommentBuilder from(
            @Nonnull CatalyticActivityComment instance) {
        physiologicalReactions.clear();
        return this.molecule(instance.getMolecule())
                .physiologicalReactions(instance.getPhysiologicalReactions())
                .reaction(instance.getReaction());
    }

    public CatalyticActivityCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CatalyticActivityCommentBuilder reaction(Reaction reaction) {
        this.reaction = reaction;
        return this;
    }

    public CatalyticActivityCommentBuilder physiologicalReactions(
            List<PhysiologicalReaction> physiologicalReactions) {
        this.physiologicalReactions = modifiableList(physiologicalReactions);
        return this;
    }

    public CatalyticActivityCommentBuilder addPhysiologicalReaction(
            PhysiologicalReaction physiologicalReaction) {
        addOrIgnoreNull(physiologicalReaction, this.physiologicalReactions);
        return this;
    }
}
