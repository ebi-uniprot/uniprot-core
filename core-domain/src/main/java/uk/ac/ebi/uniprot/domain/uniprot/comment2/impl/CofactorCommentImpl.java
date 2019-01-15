package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.CofactorCommentBuilder;

import java.util.Collections;
import java.util.List;

public class CofactorCommentImpl extends CommentImpl implements CofactorComment {
    private String molecule;
    private List<Cofactor> cofactors;
    private Note note;

    private CofactorCommentImpl() {
        super(CommentType.COFACTOR);
        this.cofactors = Collections.emptyList();
    }

    public CofactorCommentImpl(CofactorCommentBuilder builder) {
        super(CommentType.COFACTOR);
        if (builder.getMolecule() == null || builder.getMolecule().isEmpty())
            this.molecule = null;
        else
            this.molecule = builder.getMolecule();
        if ((cofactors == null) || cofactors.isEmpty()) {
            this.cofactors = Collections.emptyList();
        } else {
            this.cofactors = Collections.unmodifiableList(builder.getCofactors());
        }
        this.note = builder.getNote();
    }

    @Override
    public String getMolecule() {
        return molecule;
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
    public boolean isValid() {
        return !getCofactors().isEmpty() || (note != null);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cofactors == null) ? 0 : cofactors.hashCode());
        result = prime * result + ((molecule == null) ? 0 : molecule.hashCode());
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
        CofactorCommentImpl other = (CofactorCommentImpl) obj;
        if (cofactors == null) {
            if (other.cofactors != null)
                return false;
        } else if (!cofactors.equals(other.cofactors))
            return false;
        if (molecule == null) {
            if (other.molecule != null)
                return false;
        } else if (!molecule.equals(other.molecule))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }
}
