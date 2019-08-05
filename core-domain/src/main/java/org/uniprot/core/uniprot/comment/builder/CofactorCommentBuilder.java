package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.CofactorCommentImpl;

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

    public CofactorCommentBuilder addCofactor(Cofactor cofactors) {
        nonNullAdd(cofactors, this.cofactors);
        return this;
    }

    public CofactorCommentBuilder cofactors(List<Cofactor> cofactors) {
        this.cofactors = nonNullList(cofactors);
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
