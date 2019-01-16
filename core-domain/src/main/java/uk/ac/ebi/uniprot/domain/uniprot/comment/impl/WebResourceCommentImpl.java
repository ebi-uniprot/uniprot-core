package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;

import java.util.Objects;

public class WebResourceCommentImpl extends CommentImpl implements WebResourceComment {
    private String resourceName;
    private String resourceUrl;
    private boolean ftp;
    private String note;

    private WebResourceCommentImpl() {
        super(CommentType.WEBRESOURCE);
        this.note = "";
        this.resourceName = "";
        this.resourceUrl = "";
    }

    public WebResourceCommentImpl(WebResourceCommentBuilder builder) {
        super(CommentType.WEBRESOURCE);
        this.resourceName = builder.getResourceName();
        if (builder.getResourceUrl() == null || builder.getResourceUrl().isEmpty()) {
            this.resourceUrl = "";
        } else {
            this.resourceUrl = builder.getResourceUrl();
        }

        this.ftp = builder.isFtp();
        if (builder.getNote() == null || builder.getNote().isEmpty()) {
            this.note = "";
        } else {
            this.note = builder.getNote();
        }
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public String getResourceUrl() {
        return resourceUrl;
    }

    @Override
    public boolean isFtp() {
        return ftp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WebResourceCommentImpl that = (WebResourceCommentImpl) o;
        return ftp == that.ftp &&
                Objects.equals(resourceName, that.resourceName) &&
                Objects.equals(resourceUrl, that.resourceUrl) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resourceName, resourceUrl, ftp, note);
    }
}
