package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentImpl.isFreeTextCommentType;
import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class FreeTextCommentBuilder implements CommentBuilder<FreeTextComment> {
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

    public static @Nonnull FreeTextCommentBuilder from(@Nonnull FreeTextComment instance) {
        return new FreeTextCommentBuilder()
                .commentType(instance.getCommentType())
                .textsSet(instance.getTexts())
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

    public @Nonnull FreeTextCommentBuilder textsSet(List<EvidencedValue> texts) {
        this.texts = modifiableList(texts);
        return this;
    }

    public @Nonnull FreeTextCommentBuilder textsAdd(EvidencedValue text) {
        addOrIgnoreNull(text, this.texts);
        return this;
    }
}
