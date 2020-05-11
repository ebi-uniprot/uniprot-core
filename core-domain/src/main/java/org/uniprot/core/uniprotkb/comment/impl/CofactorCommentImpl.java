package org.uniprot.core.uniprotkb.comment.impl;

import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;

public class CofactorCommentImpl extends CommentHasMoleculeImpl implements CofactorComment {
    private static final long serialVersionUID = -2902168556405341703L;
    private List<Cofactor> cofactors;
    private Note note;

    // no arg constructor for JSON deserialization
    CofactorCommentImpl() {
        this(null, null, null);
    }

    CofactorCommentImpl(String molecule, List<Cofactor> cofactors, Note note) {
        super(CommentType.COFACTOR, molecule);
        this.cofactors = Utils.unmodifiableList(cofactors);
        this.note = note;
    }

    @Override
    public List<Cofactor> getCofactors() {
        return cofactors;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean hasCofactors() {
        return Utils.notNullNotEmpty(this.cofactors);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean isValid() {
        return !getCofactors().isEmpty() || (note != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CofactorCommentImpl that = (CofactorCommentImpl) o;
        return Objects.equals(cofactors, that.cofactors) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cofactors, note);
    }
}
