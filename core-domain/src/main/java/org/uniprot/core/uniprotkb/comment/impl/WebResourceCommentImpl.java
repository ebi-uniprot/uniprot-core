package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.emptyOrString;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.WebResourceComment;
import org.uniprot.core.util.Utils;

import java.util.Objects;

public class WebResourceCommentImpl extends CommentHasMoleculeImpl implements WebResourceComment {
    private static final long serialVersionUID = -2748929647045369784L;
    private String resourceName;
    private String resourceUrl;
    private boolean ftp;
    private String note;

    // no arg constructor for JSON deserialization
    WebResourceCommentImpl() {
        this(null, null, null, false, null);
    }

    WebResourceCommentImpl(
            String molecule, String resourceName, String resourceUrl, boolean ftp, String note) {
        super(CommentType.WEBRESOURCE, molecule);
        this.resourceName = emptyOrString(resourceName);
        this.resourceUrl = emptyOrString(resourceUrl);
        this.ftp = ftp;
        this.note = emptyOrString(note);
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
        return Utils.notNullNotEmpty(this.resourceUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WebResourceCommentImpl that = (WebResourceCommentImpl) o;
        return ftp == that.ftp
                && Objects.equals(resourceName, that.resourceName)
                && Objects.equals(resourceUrl, that.resourceUrl)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resourceName, resourceUrl, ftp, note);
    }
}
