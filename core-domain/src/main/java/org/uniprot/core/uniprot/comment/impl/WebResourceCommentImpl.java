package org.uniprot.core.uniprot.comment.impl;

import static org.uniprot.core.util.Utils.nullToEmpty;

import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.util.Utils;

public class WebResourceCommentImpl extends CommentImpl implements WebResourceComment {
    private static final long serialVersionUID = -2748929647045369784L;
    private String resourceName;
    private String resourceUrl;
    private boolean ftp;
    private String note;

    private WebResourceCommentImpl() {
        this(null, null, false, null);
    }

    public WebResourceCommentImpl(String resourceName,
                                  String resourceUrl,
                                  boolean ftp,
                                  String note) {
        super(CommentType.WEBRESOURCE);
        this.resourceName = nullToEmpty(resourceName);
        this.resourceUrl = nullToEmpty(resourceUrl);
        this.ftp = ftp;
        this.note = nullToEmpty(note);
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
    public boolean hasResourceName() {
        return this.resourceName != null;
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasResourceUrl() {
        return Utils.notEmpty(this.resourceUrl);
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