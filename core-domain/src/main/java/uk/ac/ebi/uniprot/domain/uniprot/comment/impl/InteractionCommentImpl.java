package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.common.Utils;

public class InteractionCommentImpl extends CommentImpl implements InteractionComment {
    private static final long serialVersionUID = 460447737850135638L;
    private List<Interaction> interactions;

    private InteractionCommentImpl() {
        super(CommentType.INTERACTION);
        this.interactions = Collections.emptyList();
    }

    public InteractionCommentImpl(List<Interaction> interactions) {
        super(CommentType.INTERACTION);
        if ((interactions == null) || interactions.isEmpty()) {
            this.interactions = Collections.emptyList();
        } else {
            this.interactions = Collections.unmodifiableList(interactions);
        }
    }

    @Override
    public List<Interaction> getInteractions() {
        return interactions;
    }

    @Override
    public boolean hasInteractions() {
        return Utils.notEmpty(this.interactions);
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
