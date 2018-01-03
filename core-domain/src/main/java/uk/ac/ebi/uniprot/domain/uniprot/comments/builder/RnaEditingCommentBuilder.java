package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.RnaEditingCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class RnaEditingCommentBuilder implements CommentBuilder<RnaEditingComment>{
    private  RnaEditingLocationType locationType;
    private  List<Position> locations;
    private  CommentNote note;
    
    public static RnaEditingCommentBuilder newInstance(){
        return new RnaEditingCommentBuilder();
    }
    public RnaEditingComment build(){
        return new RnaEditingCommentImpl( locationType, locations,
                 note) ;
    }
    public RnaEditingCommentBuilder setRnaEditingLocationType(RnaEditingLocationType locationType){
        this.locationType = locationType;
        return this;
    }
    public RnaEditingCommentBuilder setLocations(List<Position> locations){
        this.locations = locations;
        return this;
    }
    public RnaEditingCommentBuilder setNote(CommentNote note){
        this.note = note;
        return this;
    }
    public static Position createPosition(String position, List<Evidence> evidences) {
        return RnaEditingCommentImpl.createPosition(position, evidences);
    }
}
