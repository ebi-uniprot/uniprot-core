package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.CofactorCommentBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CofactorCommentImpl extends CommentImpl implements CofactorComment, Serializable {
    private static final long serialVersionUID = 995432515780526740L;
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
