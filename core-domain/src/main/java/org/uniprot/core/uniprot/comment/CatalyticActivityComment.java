package org.uniprot.core.uniprot.comment;

import java.util.List;

public interface CatalyticActivityComment extends Comment, HasMolecule {
    Reaction getReaction();

    List<PhysiologicalReaction> getPhysiologicalReactions();

    boolean hasReaction();

    boolean hasPhysiologicalReactions();
}
