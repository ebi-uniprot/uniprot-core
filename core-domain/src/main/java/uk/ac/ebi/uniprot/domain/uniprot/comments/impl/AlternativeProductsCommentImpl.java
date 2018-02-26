package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.comments.APEvent;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;

public class AlternativeProductsCommentImpl extends CommentImpl implements AlternativeProductsComment {
    public static APEvent createEvent(String val){
        return new APEventImpl(val);
    }
  
    private final List<APEvent> events;
    private final List<APIsoform> isoforms;
    private final Optional<CommentNote> note;
    public AlternativeProductsCommentImpl(List<APEvent> events, 
            List<APIsoform> isoforms, CommentNote note  ) {
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
        this.note = (note == null)? Optional.empty():  Optional.of(note);
    }

    @Override
    public List<APEvent> getEvents() {
       return events;
    }

    @Override
    public List<APIsoform> getIsoforms() {
       return isoforms;
    }

    @Override
    public Optional<CommentNote> getNote() {
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



    static class APEventImpl extends ValueImpl implements APEvent{

        public APEventImpl(String value) {
            super(value);          
        }
        
    }

}
