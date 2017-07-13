package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentText;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TextOnlyComment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Emilio Salazar Date: 22-Nov-2005
 */
public abstract class TextOnlyCommentImpl extends CommentImpl implements TextOnlyComment {

    protected String value = "";
    private List<CommentText> texts = new ArrayList<>();

    protected TextOnlyCommentImpl() {
        this.value = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value != null) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("That value is null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        final TextOnlyCommentImpl that = (TextOnlyCommentImpl) o;
        if (!value.equals(that.value))
            return false;

        if (texts != null && texts.size() > 0 ? !texts.equals(that.texts) : that.texts != null && that.texts.size() > 0)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + value.hashCode();
        result = 29 * result + texts.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getCommentType().toDisplayName()).append("\n");
        for (CommentText val : getTexts()) {
            sb.append(val.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<CommentText> getTexts() {
        return this.texts;
    }

}
