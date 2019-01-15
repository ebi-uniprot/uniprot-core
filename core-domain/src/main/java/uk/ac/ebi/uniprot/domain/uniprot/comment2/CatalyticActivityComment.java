package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import java.util.List;

public interface CatalyticActivityComment extends Comment {
    Reaction getReaction();

    List<PhysiologicalReaction> getPhysiologicalReactions();
}
