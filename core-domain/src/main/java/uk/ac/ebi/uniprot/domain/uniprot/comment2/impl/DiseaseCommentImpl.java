package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;

import java.io.Serializable;
import java.util.Objects;

public class DiseaseCommentImpl extends CommentImpl implements DiseaseComment, Serializable {
    private static final long serialVersionUID = 287089488618585939L;
    private Disease disease;
    private Note note;

    private DiseaseCommentImpl() {
        super(CommentType.DISEASE);
    }

    public DiseaseCommentImpl(Disease disease, Note note) {
        super(CommentType.DISEASE);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiseaseCommentImpl that = (DiseaseCommentImpl) o;
        return Objects.equals(disease, that.disease) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), disease, note);
    }
}
