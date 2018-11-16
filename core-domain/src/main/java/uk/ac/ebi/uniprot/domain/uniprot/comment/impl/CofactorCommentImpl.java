package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class CofactorCommentImpl extends CommentImpl implements CofactorComment {
    private final Optional<String> molecule;
    private final  List<Cofactor> cofactors;
    private final Optional<Note> note;
    public CofactorCommentImpl(String molecule, List<Cofactor> cofactors, Note note) {
        super(CommentType.COFACTOR);
        this.molecule = ((molecule ==null )|| molecule.isEmpty())? Optional.empty() : Optional.of(molecule);
        if((cofactors ==null) || cofactors.isEmpty()){
            this.cofactors = Collections.emptyList();
        }else{
            this.cofactors =Collections.unmodifiableList(cofactors);
        }
        this.note = (note == null)? Optional.empty():  Optional.of(note);


    }

    @Override
    public Optional<String> getMolecule() {
        return molecule;
    }

    @Override
    public List<Cofactor> getCofactors() {
        return cofactors;
    }

    @Override
    public Optional<Note> getNote() {
        return note;
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
