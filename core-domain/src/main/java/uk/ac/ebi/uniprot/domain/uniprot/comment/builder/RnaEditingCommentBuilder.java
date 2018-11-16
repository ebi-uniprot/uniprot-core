package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class RnaEditingCommentBuilder implements CommentBuilder<RnaEditingComment>{
    private  RnaEditingLocationType locationType;
    private  List<Position> locations;
    private  Note note;
    
    public static RnaEditingCommentBuilder newInstance(){
        return new RnaEditingCommentBuilder();
    }
    public RnaEditingComment build(){
        return new RnaEditingCommentImpl( locationType, locations,
                 note) ;
    }
    public RnaEditingCommentBuilder rnaEditingLocationType(RnaEditingLocationType locationType){
        this.locationType = locationType;
        return this;
    }
    public RnaEditingCommentBuilder locations(List<Position> locations){
        this.locations = locations;
        return this;
    }
    public RnaEditingCommentBuilder note(Note note){
        this.note = note;
        return this;
    }
    public static Position createPosition(String position, List<Evidence> evidences) {
        return RnaEditingCommentImpl.createPosition(position, evidences);
    }
}
