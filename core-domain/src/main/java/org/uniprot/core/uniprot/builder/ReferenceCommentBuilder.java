package org.uniprot.core.uniprot.builder;

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
    public ReferenceComment build() {
        return new ReferenceCommentImpl(type, value, evidences);
    }

    @Override
    protected ReferenceCommentBuilder getThis() {
        return this;
    }

    public ReferenceCommentBuilder type(ReferenceCommentType type) {
        this.type = type;
        return this;
    }
}
