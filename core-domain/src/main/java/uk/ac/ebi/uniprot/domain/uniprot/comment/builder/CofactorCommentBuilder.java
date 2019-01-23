package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorCommentImpl;

import java.util.ArrayList;
import java.util.List;

public final class CofactorCommentBuilder implements CommentBuilder<CofactorCommentBuilder, CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors = new ArrayList<>();
    private Note note;

    public CofactorComment build() {
        return new CofactorCommentImpl(molecule, cofactors, note);
    }

    @Override
    public CofactorCommentBuilder from(CofactorComment instance) {
        cofactors.clear();
        return this
                .cofactors(instance.getCofactors())
                .molecule(instance.getMolecule())
                .note(instance.getNote());
    }

    public CofactorCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CofactorCommentBuilder cofactors(List<Cofactor> cofactors) {
        this.cofactors.addAll(cofactors);
        return this;
    }

    public CofactorCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public String getMolecule() {
        return molecule;
    }

    public List<Cofactor> getCofactors() {
        return cofactors;
    }

    public Note getNote() {
        return note;
    }
}
