package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReferenceCommentImpl extends EvidencedValueImpl implements ReferenceComment {
    private final ReferenceCommentType type;
    @JsonCreator
    public ReferenceCommentImpl(@JsonProperty("type") ReferenceCommentType type, 
    		@JsonProperty("value") String value, 
    		@JsonProperty("evidences") List<Evidence> evidences) {
        super(value, evidences);
        this.type = type;
    }

    @Override
    public ReferenceCommentType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReferenceCommentImpl other = (ReferenceCommentImpl) obj;
        if (type != other.type)
            return false;
        return true;
    }

}
