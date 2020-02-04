package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.util.Utils;

public class AlternativeProductsCommentImpl extends CommentImpl
        implements AlternativeProductsComment {
    private static final long serialVersionUID = -8166142214330716494L;
    private List<APEventType> events;
    private List<APIsoform> isoforms;
    private Note note;

    // no arg constructor for JSON deserialization
    AlternativeProductsCommentImpl() {
        super(CommentType.ALTERNATIVE_PRODUCTS);
        this.events = Collections.emptyList();
        this.isoforms = Collections.emptyList();
    }

    public AlternativeProductsCommentImpl(
            List<APEventType> events, List<APIsoform> isoforms, Note note) {
        super(CommentType.ALTERNATIVE_PRODUCTS);
        if ((events == null) || events.isEmpty()) {
            this.events = Collections.emptyList();
        } else {
            this.events = Collections.unmodifiableList(events);
        }
        if ((isoforms == null) || isoforms.isEmpty()) {
            this.isoforms = Collections.emptyList();
        } else {
            this.isoforms = Collections.unmodifiableList(isoforms);
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
    public boolean hasEvents() {
        return Utils.notNullNotEmpty(this.events);
    }

    @Override
    public boolean hasIsoforms() {
        return Utils.notNullNotEmpty(this.isoforms);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AlternativeProductsCommentImpl that = (AlternativeProductsCommentImpl) o;
        return Objects.equals(events, that.events)
                && Objects.equals(isoforms, that.isoforms)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), events, isoforms, note);
    }
}
