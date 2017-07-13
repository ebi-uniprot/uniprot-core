package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentText;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TextOnlyComment;

import java.util.List;

public class AllergenCommentImpl extends TextOnlyCommentImpl implements AllergenComment, TextOnlyComment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AllergenCommentImpl() {
        super();
        this.setCommentType(CommentType.ALLERGEN);
    }

    @Override
    public CommentType getCommentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CommentText> getTexts() {
        // TODO Auto-generated method stub
        return null;
    }
}