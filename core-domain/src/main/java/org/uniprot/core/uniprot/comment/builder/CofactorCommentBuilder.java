package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.CofactorCommentImpl;

import javax.annotation.Nonnull;

public final class CofactorCommentBuilder
        implements CommentBuilder<CofactorCommentBuilder, CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors = new ArrayList<>();
    private Note note;

    public @Nonnull CofactorComment build() {
        return new CofactorCommentImpl(molecule, cofactors, note);
    }

    @Override
    public @Nonnull CofactorCommentBuilder from(@Nonnull CofactorComment instance) {
        cofactors.clear();
        return this.cofactors(instance.getCofactors())
                .molecule(instance.getMolecule())
                .note(instance.getNote());
    }

    public CofactorCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CofactorCommentBuilder addCofactor(Cofactor cofactors) {
        addOrIgnoreNull(cofactors, this.cofactors);
        return this;
    }

    public CofactorCommentBuilder cofactors(List<Cofactor> cofactors) {
        this.cofactors = modifiableList(cofactors);
        return this;
    }

    public CofactorCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
