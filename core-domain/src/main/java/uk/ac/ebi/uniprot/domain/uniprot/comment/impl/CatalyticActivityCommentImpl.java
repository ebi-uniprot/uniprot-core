package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;

import java.util.Collections;
import java.util.List;

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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((physiologicalReactions == null) ? 0 : physiologicalReactions.hashCode());
        result = prime * result + ((reaction == null) ? 0 : reaction.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatalyticActivityCommentImpl other = (CatalyticActivityCommentImpl) obj;
        if (physiologicalReactions == null) {
            if (other.physiologicalReactions != null)
                return false;
        } else if (!physiologicalReactions.equals(other.physiologicalReactions))
            return false;
        if (reaction == null) {
            if (other.reaction != null)
                return false;
        } else if (!reaction.equals(other.reaction))
            return false;
        return true;
    }

}
