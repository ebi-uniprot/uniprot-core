package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.APCommentBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AlternativeProductsCommentImpl that = (AlternativeProductsCommentImpl) o;
        return Objects.equals(events, that.events) &&
                Objects.equals(isoforms, that.isoforms) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), events, isoforms, note);
    }
}
