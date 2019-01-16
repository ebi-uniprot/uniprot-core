package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.InteractionComment;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InteractionCommentImpl extends CommentImpl implements InteractionComment {
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
