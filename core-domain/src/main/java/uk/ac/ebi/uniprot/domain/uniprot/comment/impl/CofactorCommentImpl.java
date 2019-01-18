package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CofactorCommentImpl that = (CofactorCommentImpl) o;
        return Objects.equals(molecule, that.molecule) &&
                Objects.equals(cofactors, that.cofactors) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), molecule, cofactors, note);
    }
}
