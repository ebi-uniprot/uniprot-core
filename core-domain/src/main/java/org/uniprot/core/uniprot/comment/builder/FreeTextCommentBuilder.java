package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.uniprot.comment.impl.FreeTextCommentImpl.isFreeTextCommentType;
import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.comment.impl.FreeTextCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class FreeTextCommentBuilder
        implements CommentBuilder<FreeTextCommentBuilder, FreeTextComment> {
    private CommentType commentType;
    private String molecule;
    private List<EvidencedValue> texts = new ArrayList<>();

    @Override
    public @Nonnull FreeTextComment build() {
        if (!isFreeTextCommentType(commentType)) {
            throw new IllegalArgumentException(commentType + " is not free text comment");
        }
        return new FreeTextCommentImpl(commentType, molecule, texts);
    }

    @Override
    public @Nonnull FreeTextCommentBuilder from(@Nonnull FreeTextComment instance) {
        texts.clear();
        return this.commentType(instance.getCommentType())
                .texts(instance.getTexts())
                .molecule(instance.getMolecule());
    }

    public @Nonnull FreeTextCommentBuilder commentType(CommentType commentType) {
        this.commentType = commentType;
        return this;
    }

    public @Nonnull FreeTextCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull FreeTextCommentBuilder texts(List<EvidencedValue> texts) {
        this.texts = modifiableList(texts);
        return this;
    }

    public @Nonnull FreeTextCommentBuilder addText(EvidencedValue text) {
        addOrIgnoreNull(text, this.texts);
        return this;
    }
}
