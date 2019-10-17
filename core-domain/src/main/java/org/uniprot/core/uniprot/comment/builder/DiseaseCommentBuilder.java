package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentImpl;

import javax.annotation.Nonnull;

public class DiseaseCommentBuilder
        implements CommentBuilder<DiseaseCommentBuilder, DiseaseComment> {
    private Disease disease;
    private Note note;

    @Override
    public @Nonnull DiseaseComment build() {
        return new DiseaseCommentImpl(disease, note);
    }

    @Override
    public @Nonnull DiseaseCommentBuilder from(@Nonnull DiseaseComment instance) {
        return this.disease(instance.getDisease()).note(instance.getNote());
    }

    public DiseaseCommentBuilder disease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public DiseaseCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
