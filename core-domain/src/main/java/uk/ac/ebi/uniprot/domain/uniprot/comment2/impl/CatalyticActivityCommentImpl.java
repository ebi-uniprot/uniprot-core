package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Reaction;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CatalyticActivityCommentImpl extends CommentImpl implements CatalyticActivityComment {
    private Reaction reaction;
    private List<PhysiologicalReaction> physiologicalReactions;

    private CatalyticActivityCommentImpl() {
        super(CommentType.CATALYTIC_ACTIVITY);
        this.physiologicalReactions = Collections.emptyList();
    }

    public CatalyticActivityCommentImpl(Reaction reaction,
                                        List<PhysiologicalReaction> physiologicalReactions) {
        super(CommentType.CATALYTIC_ACTIVITY);
        this.reaction = reaction;
        if ((physiologicalReactions == null) || physiologicalReactions.isEmpty()) {
            this.physiologicalReactions = Collections.emptyList();
        } else {
            this.physiologicalReactions = Collections.unmodifiableList(physiologicalReactions);
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatalyticActivityCommentImpl that = (CatalyticActivityCommentImpl) o;
        return Objects.equals(reaction, that.reaction) &&
                Objects.equals(physiologicalReactions, that.physiologicalReactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reaction, physiologicalReactions);
    }
}
