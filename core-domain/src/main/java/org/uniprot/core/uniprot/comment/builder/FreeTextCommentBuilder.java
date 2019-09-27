package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.uniprot.comment.impl.FreeTextCommentImpl.isFreeTextCommentType;
import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.comment.impl.FreeTextCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class FreeTextCommentBuilder
        implements CommentBuilder<FreeTextCommentBuilder, FreeTextComment> {
    private CommentType commentType;
    private List<EvidencedValue> texts = new ArrayList<>();

    @Override
    public FreeTextComment build() {
        if (!isFreeTextCommentType(commentType)) {
            throw new IllegalArgumentException(commentType + " is not free text comment");
        }
        return new FreeTextCommentImpl(commentType, texts);
    }

    @Override
    public FreeTextCommentBuilder from(FreeTextComment instance) {
        texts.clear();
        return this.commentType(instance.getCommentType()).texts(instance.getTexts());
    }

    public FreeTextCommentBuilder commentType(CommentType commentType) {
        this.commentType = commentType;
        return this;
    }

    public FreeTextCommentBuilder texts(List<EvidencedValue> texts) {
        this.texts = nonNullList(texts);
        return this;
    }

    public FreeTextCommentBuilder addText(EvidencedValue text) {
        nonNullAdd(text, this.texts);
        return this;
    }
}
