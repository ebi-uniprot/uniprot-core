package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseCommentImpl;

public class DiseaseCommentBuilder implements CommentBuilder<DiseaseCommentBuilder, DiseaseComment> {
    private Disease disease;
    private Note note;

    @Override
    public DiseaseComment build() {
        return new DiseaseCommentImpl(disease, note);
    }

    @Override
    public DiseaseCommentBuilder from(DiseaseComment instance) {
        return new DiseaseCommentBuilder()
                .disease(instance.getDisease())
                .note(instance.getNote());
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
