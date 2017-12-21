package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;

import java.util.List;

public class CommentNoteImpl extends FreeTextImpl implements CommentNote {
    public CommentNoteImpl(List<EvidencedValue> texts) {
        super(texts);   
    }

}
