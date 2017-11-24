package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;

import java.util.List;

public class AllergenCommentImpl extends TextOnlyCommentImpl implements AllergenComment {

    public AllergenCommentImpl( List<EvidencedValue> texts) {
        super(CommentType.ALLERGEN, texts);
    }

}