package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface PhysiologicalReaction extends HasEvidences {
    PhysiologicalDirectionType getDirectionType();

    DBCrossReference<ReactionReferenceType> getReactionReference();

    boolean hasDirectionType();

    boolean hasReactionReference();
}
