package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ReferenceCommentImpl;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class ReferenceCommentBuilder extends AbstractEvidencedValueBuilder<ReferenceCommentBuilder, ReferenceComment> {
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
