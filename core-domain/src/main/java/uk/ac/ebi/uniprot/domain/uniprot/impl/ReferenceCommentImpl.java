package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReferenceCommentImpl extends EvidencedValueImpl implements ReferenceComment {
    private static final long serialVersionUID = 6546262222329573992L;
    private ReferenceCommentType type;

    private ReferenceCommentImpl() {
        super("", Collections.emptyList());
    }

    public ReferenceCommentImpl(ReferenceCommentType type,
                                String value,
                                List<Evidence> evidences) {
        super(value, evidences);
        this.type = type;
    }

    @Override
    public ReferenceCommentType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReferenceCommentImpl that = (ReferenceCommentImpl) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}
