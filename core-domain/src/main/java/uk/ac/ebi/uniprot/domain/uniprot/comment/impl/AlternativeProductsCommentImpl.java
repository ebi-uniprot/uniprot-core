package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AlternativeProductsCommentImpl extends CommentImpl implements AlternativeProductsComment {
 
    private final List<APEventType> events;
    private final List<APIsoform> isoforms;
    private final Note note;
	@JsonCreator
    public AlternativeProductsCommentImpl(
    		@JsonProperty("events") List<APEventType> events, 
    		@JsonProperty("isoforms") List<APIsoform> isoforms, 
    		@JsonProperty("note") Note note  ) {
        super(CommentType.ALTERNATIVE_PRODUCTS);
        if((events ==null) || events.isEmpty()){
            this.events = Collections.emptyList();
        }else{
            this.events =Collections.unmodifiableList(events);
        }
        if((isoforms ==null) || isoforms.isEmpty()){
            this.isoforms = Collections.emptyList();
        }else{
            this.isoforms =Collections.unmodifiableList(isoforms);
        }
        this.note = note;
    }

    @Override
    public List<APEventType> getEvents() {
       return events;
    }

    @Override
    public List<APIsoform> getIsoforms() {
       return isoforms;
    }

    @Override
    public Note getNote() {
        return note;
    }
    
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((isoforms == null) ? 0 : isoforms.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlternativeProductsCommentImpl other = (AlternativeProductsCommentImpl) obj;
        if (events == null) {
            if (other.events != null)
                return false;
        } else if (!events.equals(other.events))
            return false;
        if (isoforms == null) {
            if (other.isoforms != null)
                return false;
        } else if (!isoforms.equals(other.isoforms))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }



}
