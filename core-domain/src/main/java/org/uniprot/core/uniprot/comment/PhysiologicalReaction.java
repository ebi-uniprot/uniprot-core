package org.uniprot.core.uniprot.comment;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface PhysiologicalReaction extends HasEvidences {
    PhysiologicalDirectionType getDirectionType();

    DBCrossReference<ReactionDatabase> getReactionReference();

    boolean hasDirectionType();

    boolean hasReactionReference();
}
