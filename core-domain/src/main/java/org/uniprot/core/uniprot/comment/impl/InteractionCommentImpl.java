package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.util.Utils;

public class InteractionCommentImpl extends CommentImpl implements InteractionComment {
    private static final long serialVersionUID = 460447737850135638L;
    private List<Interaction> interactions;

    // no arg constructor for JSON deserialization
    InteractionCommentImpl() {
        super(CommentType.INTERACTION);
        this.interactions = Collections.emptyList();
    }

    InteractionCommentImpl(List<Interaction> interactions) {
        super(CommentType.INTERACTION);
        this.interactions = Utils.unmodifiableList(interactions);
    }

    @Override
    public List<Interaction> getInteractions() {
        return interactions;
    }

    @Override
    public boolean hasInteractions() {
        return Utils.notNullNotEmpty(this.interactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InteractionCommentImpl that = (InteractionCommentImpl) o;
        return Objects.equals(interactions, that.interactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interactions);
    }
}
