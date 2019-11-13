package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.impl.WebResourceCommentImpl;

import javax.annotation.Nonnull;

public final class WebResourceCommentBuilder
        implements CommentBuilder<WebResourceCommentBuilder, WebResourceComment> {
    private String resourceName;
    private String resourceUrl;
    private boolean isFtp = false;
    private String note;

    public @Nonnull WebResourceComment build() {
        return new WebResourceCommentImpl(resourceName, resourceUrl, isFtp, note);
    }

    @Override
    public @Nonnull WebResourceCommentBuilder from(@Nonnull WebResourceComment instance) {
        return this.isFtp(instance.isFtp())
                .note(instance.getNote())
                .resourceName(instance.getResourceName())
                .resourceUrl(instance.getResourceUrl());
    }

    public @Nonnull WebResourceCommentBuilder resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public @Nonnull WebResourceCommentBuilder resourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public @Nonnull WebResourceCommentBuilder isFtp(boolean isFtp) {
        this.isFtp = isFtp;
        return this;
    }

    public @Nonnull WebResourceCommentBuilder note(String note) {
        this.note = note;
        return this;
    }
}
