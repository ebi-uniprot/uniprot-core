package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CofactorCommentImpl extends CommentImpl implements CofactorComment {
    private static final long serialVersionUID = -2902168556405341703L;
    private String molecule;
    private List<Cofactor> cofactors;
    private Note note;

    private CofactorCommentImpl() {
        this(null,null,null);
    }

    public CofactorCommentImpl(String molecule,
                               List<Cofactor> cofactors,
                               Note note) {
        super(CommentType.COFACTOR);
        if(molecule == null || molecule.isEmpty())
            this.molecule =null;
        else
            this.molecule =molecule;
        if((cofactors ==null) || cofactors.isEmpty()){
            this.cofactors = Collections.emptyList();
        }else{
            this.cofactors =Collections.unmodifiableList(cofactors);
        }
        this.note =note;
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
    public boolean hasMolecule() {
        return Utils.notEmpty(this.molecule);
    }

    @Override
    public boolean hasCofactors() {
        return Utils.notEmpty(this.cofactors);
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
        return Objects.equals(molecule, that.molecule) &&
                Objects.equals(cofactors, that.cofactors) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), molecule, cofactors, note);
    }
}
