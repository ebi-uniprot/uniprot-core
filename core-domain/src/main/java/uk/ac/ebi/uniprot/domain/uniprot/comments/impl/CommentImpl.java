package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.ArrayList;
import java.util.List;

public class CommentImpl implements Comment {

    private CommentType type;
    private List<Evidence> evidences;

    public CommentImpl() {
        this.type = null;
        this.evidences = new ArrayList<>();
    }

    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Evidence> getEvidences() {
        if (evidences == null)
            this.evidences = new ArrayList<>();
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        if (evidences != null) {
            this.evidences = evidences;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public CommentType getCommentType() {
        return this.type;
    }

    public void setCommentType(CommentType type) {
        if (type != null) {
            this.type = type;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final CommentImpl comment = (CommentImpl) o;

        if (evidences != null && evidences.size() > 0 ? !evidences.equals(comment.evidences)
                : comment.evidences != null && comment.evidences.size() > 0)
            return false;
        if (type != comment.type)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (type != null ? type.hashCode() : 0);
        result = 29 * result + (evidences != null && evidences.size() > 0 ? evidences.hashCode() : 0);
        return result;
    }

}
