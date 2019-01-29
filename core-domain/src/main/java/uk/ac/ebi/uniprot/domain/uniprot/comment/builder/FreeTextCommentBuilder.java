package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl.isFreeTextCommentType;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public class FreeTextCommentBuilder implements CommentBuilder<FreeTextCommentBuilder, FreeTextComment> {
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
        return this
                .commentType(instance.getCommentType())
                .texts(instance.getTexts());
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
