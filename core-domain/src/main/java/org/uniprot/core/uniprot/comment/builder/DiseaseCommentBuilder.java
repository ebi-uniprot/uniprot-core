package org.uniprot.core.uniprot.comment.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentImpl;

public class DiseaseCommentBuilder
        implements CommentBuilder<DiseaseCommentBuilder, DiseaseComment> {
    String molecule;
    private Disease disease;
    private Note note;

    @Override
    public @Nonnull DiseaseComment build() {
        return new DiseaseCommentImpl(molecule, disease, note);
    }

    @Override
    public @Nonnull DiseaseCommentBuilder from(@Nonnull DiseaseComment instance) {
        return this.disease(instance.getDisease())
                .note(instance.getNote())
                .molecule(instance.getMolecule());
    }

    public @Nonnull DiseaseCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull DiseaseCommentBuilder disease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public @Nonnull DiseaseCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
