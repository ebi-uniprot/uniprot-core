package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface PhysiologicalReaction extends HasEvidences {
    PhysiologicalDirectionType getDirectionType();

    CrossReference<ReactionDatabase> getReactionCrossReference();

    boolean hasDirectionType();

    boolean hasReactionCrossReference();
}
