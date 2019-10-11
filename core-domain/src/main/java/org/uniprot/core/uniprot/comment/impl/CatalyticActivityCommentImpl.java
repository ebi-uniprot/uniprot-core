package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.util.Utils;

public class CatalyticActivityCommentImpl extends CommentImpl implements CatalyticActivityComment {
    private static final long serialVersionUID = 166685624467020599L;
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions;

    // no arg constructor for JSON deserialization
    CatalyticActivityCommentImpl() {
        super(CommentType.CATALYTIC_ACTIVITY);
        this.physiologicalReactions = Collections.emptyList();
    }

    public CatalyticActivityCommentImpl(
            Reaction reaction, List<PhysiologicalReaction> physiologicalReactions) {
        super(CommentType.CATALYTIC_ACTIVITY);
        this.reaction = reaction;
        this.physiologicalReactions = Utils.unmodifiableList(physiologicalReactions);
    }

    @Override
    public Reaction getReaction() {
        return reaction;
    }

    @Override
    public List<PhysiologicalReaction> getPhysiologicalReactions() {
        return physiologicalReactions;
    }

    @Override
    public boolean hasReaction() {
        return reaction != null;
    }

    @Override
    public boolean hasPhysiologicalReactions() {
        return Utils.notNullOrEmpty(this.physiologicalReactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatalyticActivityCommentImpl that = (CatalyticActivityCommentImpl) o;
        return Objects.equals(reaction, that.reaction)
                && Objects.equals(physiologicalReactions, that.physiologicalReactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reaction, physiologicalReactions);
    }
}
