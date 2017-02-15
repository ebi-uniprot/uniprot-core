package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface InteractionComment extends Comment {
	public List<Interaction> getInteractions();
}
