package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;

public interface ReferenceComment extends Value, HasEvidences{
    public ReferenceCommentType getType();
}
