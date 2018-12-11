package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseCommentImpl;

public class DiseaseCommentBuilder implements CommentBuilder<DiseaseComment> {
    public static DiseaseCommentBuilder newInstance(){
        return new DiseaseCommentBuilder();
    }
    public static DiseaseBuilder newDiseaseBuilder(){
        return DiseaseBuilder.newInstance();
    }
    private Disease disease;
    private Note note;
    public DiseaseCommentBuilder disease(Disease disease){
        this.disease = disease;
        return this;
    }
    public DiseaseCommentBuilder note(Note note){
        this.note = note;
        return this;
    }
    @Override
    public DiseaseComment build() {
        return new DiseaseCommentImpl(disease, note);
    }

}
