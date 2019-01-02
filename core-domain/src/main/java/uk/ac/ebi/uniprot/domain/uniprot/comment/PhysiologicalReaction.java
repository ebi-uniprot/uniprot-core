package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface PhysiologicalReaction extends HasEvidences {
    PhysiologicalDirectionType getDirectionType();

    DBCrossReference<ReactionReferenceType> getReactionReference();
}
