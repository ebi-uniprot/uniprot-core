package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhysiologicalReactionImpl implements PhysiologicalReaction {
	private final PhysiologicalDirectionType directionType;
	private final DBCrossReference<ReactionReferenceType> reactionReference;
	private final List<Evidence> evidences;
	  @JsonCreator
	public PhysiologicalReactionImpl(@JsonProperty("directionType") PhysiologicalDirectionType directionType,
			@JsonProperty("reactionReference") DBCrossReference<ReactionReferenceType> reactionReference,
			@JsonProperty("evidences") List<Evidence> evidences
			) {
		this.directionType = directionType;
		this.reactionReference =reactionReference;
		if((evidences ==null) || evidences.isEmpty()) {
			this.evidences = Collections.emptyList();
		}else {
			this.evidences = Collections.unmodifiableList(evidences);
		}
		
	}
	
	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public PhysiologicalDirectionType getDirectionType() {
		return directionType;
	}

	@Override
	public DBCrossReference<ReactionReferenceType> getReactionReference() {
		return reactionReference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directionType == null) ? 0 : directionType.hashCode());
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		result = prime * result + ((reactionReference == null) ? 0 : reactionReference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhysiologicalReactionImpl other = (PhysiologicalReactionImpl) obj;
		if (directionType != other.directionType)
			return false;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (reactionReference == null) {
			if (other.reactionReference != null)
				return false;
		} else if (!reactionReference.equals(other.reactionReference))
			return false;
		return true;
	}

}
