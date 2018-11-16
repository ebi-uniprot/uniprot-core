package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface CatalyticActivityComment extends Comment {
	Reaction getReaction();
	List<PhysiologicalReaction> getPhysiologicalReactions();
	void setReaction(Reaction reaction);
	void setPhysiologicalReactions(List<PhysiologicalReaction> physiologicalReactions);
}
