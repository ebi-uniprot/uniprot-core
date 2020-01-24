package org.uniprot.core.uniprot.comment.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder implements CommentBuilder<WebResourceComment> {
    String molecule;
    private String resourceName;
    private String resourceUrl;
    private boolean isFtp = false;
    private String note;

    public @Nonnull WebResourceComment build() {
        return new WebResourceCommentImpl(molecule, resourceName, resourceUrl, isFtp, note);
    }

    public static @Nonnull WebResourceCommentBuilder from(@Nonnull WebResourceComment instance) {
        return new WebResourceCommentBuilder().isFtp(instance.isFtp())
                .note(instance.getNote())
                .resourceName(instance.getResourceName())
                .resourceUrl(instance.getResourceUrl())
                .molecule(instance.getMolecule());
    }

    public @Nonnull WebResourceCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
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
