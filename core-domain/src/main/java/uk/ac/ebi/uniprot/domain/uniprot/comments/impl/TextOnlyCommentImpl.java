package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TextOnlyComment;

import java.util.List;

public abstract class TextOnlyCommentImpl extends FreeTextImpl implements TextOnlyComment {
    private final CommentType type;
    public TextOnlyCommentImpl(CommentType type, List<EvidencedValue> texts) {
        super(texts);
        this.type = type;
       
    }
    @Override
    public CommentType getCommentType() {
        return type;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        TextOnlyCommentImpl other = (TextOnlyCommentImpl) obj;
        if (type != other.type)
            return false;
        return true;
    }


}
