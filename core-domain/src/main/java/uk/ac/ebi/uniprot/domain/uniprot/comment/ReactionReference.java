package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.io.Serializable;

public interface ReactionReference extends Serializable {
	ReactionReferenceType getType();
	String getId();
	void setType(ReactionReferenceType type);
	void setId(String id);
}
