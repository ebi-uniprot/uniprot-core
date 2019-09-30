package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder
        implements CommentBuilder<WebResourceCommentBuilder, WebResourceComment> {
    private String resourceName;
    private String resourceUrl;
    private boolean isFtp = false;
    private String note;

    public WebResourceComment build() {
        return new WebResourceCommentImpl(resourceName, resourceUrl, isFtp, note);
    }

    @Override
    public WebResourceCommentBuilder from(WebResourceComment instance) {
        return this.isFtp(instance.isFtp())
                .note(instance.getNote())
                .resourceName(instance.getResourceName())
                .resourceUrl(instance.getResourceUrl());
    }

    public WebResourceCommentBuilder resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public WebResourceCommentBuilder resourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public WebResourceCommentBuilder isFtp(boolean isFtp) {
        this.isFtp = isFtp;
        return this;
    }

    public WebResourceCommentBuilder note(String note) {
        this.note = note;
        return this;
    }
}
