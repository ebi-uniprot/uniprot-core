package org.uniprot.core.uniprot.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.Note;

public final class CofactorCommentBuilder implements CommentBuilder<CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors = new ArrayList<>();
    private Note note;

    public @Nonnull CofactorComment build() {
        return new CofactorCommentImpl(molecule, cofactors, note);
    }

    public static @Nonnull CofactorCommentBuilder from(@Nonnull CofactorComment instance) {
        return new CofactorCommentBuilder()
                .cofactorsSet(instance.getCofactors())
                .molecule(instance.getMolecule())
                .note(instance.getNote());
    }

    public @Nonnull CofactorCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull CofactorCommentBuilder cofactorsAdd(Cofactor cofactors) {
        addOrIgnoreNull(cofactors, this.cofactors);
        return this;
    }

    public @Nonnull CofactorCommentBuilder cofactorsSet(List<Cofactor> cofactors) {
        this.cofactors = modifiableList(cofactors);
        return this;
    }

    public @Nonnull CofactorCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
