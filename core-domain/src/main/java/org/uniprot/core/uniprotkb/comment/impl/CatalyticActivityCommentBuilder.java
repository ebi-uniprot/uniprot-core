package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.Reaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class CatalyticActivityCommentBuilder implements CommentBuilder<CatalyticActivityComment> {
    private String molecule;
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions = new ArrayList<>();

    @Override
    public @Nonnull CatalyticActivityComment build() {
        return new CatalyticActivityCommentImpl(molecule, reaction, physiologicalReactions);
    }

    public static @Nonnull CatalyticActivityCommentBuilder from(
            @Nonnull CatalyticActivityComment instance) {
        return new CatalyticActivityCommentBuilder()
                .molecule(instance.getMolecule())
                .physiologicalReactionsSet(instance.getPhysiologicalReactions())
                .reaction(instance.getReaction());
    }

    public @Nonnull CatalyticActivityCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull CatalyticActivityCommentBuilder reaction(Reaction reaction) {
        this.reaction = reaction;
        return this;
    }

    public @Nonnull CatalyticActivityCommentBuilder physiologicalReactionsSet(
            List<PhysiologicalReaction> physiologicalReactions) {
        this.physiologicalReactions = modifiableList(physiologicalReactions);
        return this;
    }

    public @Nonnull CatalyticActivityCommentBuilder physiologicalReactionsAdd(
            PhysiologicalReaction physiologicalReaction) {
        addOrIgnoreNull(physiologicalReaction, this.physiologicalReactions);
        return this;
    }
}
