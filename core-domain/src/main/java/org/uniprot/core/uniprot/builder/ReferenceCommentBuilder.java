package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.ReferenceCommentImpl;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class ReferenceCommentBuilder
        extends AbstractEvidencedValueBuilder<ReferenceCommentBuilder, ReferenceComment> {
    private ReferenceCommentType type;

    @Override
    public @Nonnull ReferenceComment build() {
        return new ReferenceCommentImpl(type, value, evidences);
    }

    @Override
    protected @Nonnull ReferenceCommentBuilder getThis() {
        return this;
    }

    public @Nonnull ReferenceCommentBuilder type(ReferenceCommentType type) {
        this.type = type;
        return this;
    }

    public static @Nonnull ReferenceCommentBuilder from(@Nonnull ReferenceComment instance) {
        ReferenceCommentBuilder builder = new ReferenceCommentBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        return builder.type(instance.getType());
    }
}
