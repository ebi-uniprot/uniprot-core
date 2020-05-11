package org.uniprot.core.uniprotkb.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;

public class ReferenceCommentImpl extends EvidencedValueImpl implements ReferenceComment {
    private static final long serialVersionUID = 6546262222329573992L;
    private ReferenceCommentType type;

    // no arg constructor for JSON deserialization
    ReferenceCommentImpl() {
        super("", Collections.emptyList());
    }

    ReferenceCommentImpl(ReferenceCommentType type, String value, List<Evidence> evidences) {
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
