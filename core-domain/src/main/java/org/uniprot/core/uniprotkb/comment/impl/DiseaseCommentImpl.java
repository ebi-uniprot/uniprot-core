package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Note;

public class DiseaseCommentImpl extends CommentHasMoleculeImpl implements DiseaseComment {
    private static final long serialVersionUID = 1934540834096516974L;
    private Disease disease;
    private Note note;

    // no arg constructor for JSON deserialization
    DiseaseCommentImpl() {
        super(CommentType.DISEASE, null);
    }

    DiseaseCommentImpl(String molecule, Disease disease, Note note) {
        super(CommentType.DISEASE, molecule);
        this.disease = disease;
        this.note = note;
    }

    @Override
    public Disease getDisease() {
        return disease;
    }

    @Override
    public boolean hasDefinedDisease() {
        return ((disease != null) && disease.hasDefinedDisease());
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiseaseCommentImpl that = (DiseaseCommentImpl) o;
        return Objects.equals(disease, that.disease) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), disease, note);
    }
}
