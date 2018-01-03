package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.DiseaseCommentImpl;

public class DiseaseCommentBuilder implements CommentBuilder<DiseaseComment> {
    public static DiseaseCommentBuilder newInstance(){
        return new DiseaseCommentBuilder();
    }
    public static DiseaseBuilder newDiseaseBuilder(){
        return DiseaseBuilder.newInstance();
    }
    private Disease disease;
    private CommentNote note;
    public DiseaseCommentBuilder setDisease(Disease disease){
        this.disease = disease;
        return this;
    }
    public DiseaseCommentBuilder setNote(CommentNote note){
        this.note = note;
        return this;
    }
    @Override
    public DiseaseComment build() {
        return new DiseaseCommentImpl(disease, note);
    }

}
