package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.CommentNoteImpl;

import java.util.List;

public final class CommentBuilderUtil {
    public static CommentNote createCommentNote(List<EvidencedValue> texts) {
        return new CommentNoteImpl(texts);
    }
}
