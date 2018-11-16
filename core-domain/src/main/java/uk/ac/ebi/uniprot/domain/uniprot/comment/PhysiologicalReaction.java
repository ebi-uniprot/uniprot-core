package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface PhysiologicalReaction  extends HasEvidences{
	PhysiologicalDirectionType getDirectionType();
	ReactionReference getReactionReference();
}
