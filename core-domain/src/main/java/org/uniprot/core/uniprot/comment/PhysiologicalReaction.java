package org.uniprot.core.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface PhysiologicalReaction extends HasEvidences {
    PhysiologicalDirectionType getDirectionType();

    CrossReference<ReactionDatabase> getReactionCrossReference();

    boolean hasDirectionType();

    boolean hasReactionCrossReference();
}
