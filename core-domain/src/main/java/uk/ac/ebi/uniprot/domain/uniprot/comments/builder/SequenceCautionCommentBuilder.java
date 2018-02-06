package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.SequenceCautionCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class SequenceCautionCommentBuilder implements CommentBuilder<SequenceCautionComment> {
    private  SequenceCautionType sequenceCautionType;
    private  String sequence;
    private  List<String> positions;
    private  String note;
    private List<Evidence> evidences;
    
    public static SequenceCautionCommentBuilder newInstance(){
        return new SequenceCautionCommentBuilder();
    }
    public SequenceCautionComment build(){
        return new SequenceCautionCommentImpl( sequenceCautionType,  sequence, positions,
                 note, evidences) ;
    }
    public SequenceCautionCommentBuilder sequenceCautionType(SequenceCautionType sequenceCautionType){
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }
    
    public SequenceCautionCommentBuilder sequence(String sequence){
        this.sequence = sequence;
        return this;
    }
    
    public SequenceCautionCommentBuilder positions(List<String> positions){
        this.positions = positions;
        return this;
    }
    
    public SequenceCautionCommentBuilder note(String note){
        this.note = note;
        return this;
    }
    public SequenceCautionCommentBuilder evidences(List<Evidence> evidences){
        this.evidences = evidences;
        return this;
    }
}
