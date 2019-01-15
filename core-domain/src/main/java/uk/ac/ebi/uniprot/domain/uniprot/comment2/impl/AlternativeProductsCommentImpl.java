package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.APCommentBuilder;

import java.util.Collections;
import java.util.List;

public class AlternativeProductsCommentImpl extends CommentImpl implements AlternativeProductsComment {
    private List<APEventType> events;
    private List<APIsoform> isoforms;
    private Note note;

    private AlternativeProductsCommentImpl() {
        super(CommentType.ALTERNATIVE_PRODUCTS);
        this.events = Collections.emptyList();
        this.isoforms = Collections.emptyList();
    }

    public AlternativeProductsCommentImpl(APCommentBuilder builder) {
        super(CommentType.ALTERNATIVE_PRODUCTS);
        if ((builder.getEvents() == null) || builder.getEvents().isEmpty()) {
            this.events = Collections.emptyList();
        } else {
            this.events = Collections.unmodifiableList(events);
        }
        if ((builder.getIsoforms() == null) || builder.getIsoforms().isEmpty()) {
            this.isoforms = Collections.emptyList();
        } else {
            this.isoforms = Collections.unmodifiableList(isoforms);
        }
        this.note = builder.getNote();
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
