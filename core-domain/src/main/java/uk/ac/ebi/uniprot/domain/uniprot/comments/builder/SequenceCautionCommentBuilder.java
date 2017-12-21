package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.SequenceCautionCommentImpl;

import java.util.List;

public final class SequenceCautionCommentBuilder {
    private  SequenceCautionType sequenceCautionType;
    private  String sequence;
    private  List<String> positions;
    private  String note;
    
    public SequenceCautionComment build(){
        return new SequenceCautionCommentImpl( sequenceCautionType,  sequence, positions,
                 note) ;
    }
    public SequenceCautionCommentBuilder setSequenceCautionType(SequenceCautionType sequenceCautionType){
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }
    
    public SequenceCautionCommentBuilder setSequence(String sequence){
        this.sequence = sequence;
        return this;
    }
    
    public SequenceCautionCommentBuilder setPositions(List<String> positions){
        this.positions = positions;
        return this;
    }
    
    public SequenceCautionCommentBuilder setNote(String note){
        this.note = note;
        return this;
    }
}
