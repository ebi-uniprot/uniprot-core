package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;

public class DiseaseCommentImpl extends CommentImpl implements DiseaseComment {
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((disease == null) ? 0 : disease.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
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
        DiseaseCommentImpl other = (DiseaseCommentImpl) obj;
        if (disease == null) {
            if (other.disease != null)
                return false;
        } else if (!disease.equals(other.disease))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }

}
