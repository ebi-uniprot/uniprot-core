package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder implements CommentBuilder<WebResourceCommentBuilder, WebResourceComment> {
    private String resourceName;
    private String resourceUrl;
    private boolean isFtp = false;
    private String note;

    public static WebResourceCommentBuilder newInstance() {
        return new WebResourceCommentBuilder();
    }

    public WebResourceComment build() {
        return new WebResourceCommentImpl(this);
    }

    @Override
    public WebResourceCommentBuilder from(WebResourceComment instance) {
        return new WebResourceCommentBuilder()
                .isFtp(instance.isFtp())
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

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public boolean isFtp() {
        return isFtp;
    }

    public String getNote() {
        return note;
    }
}
