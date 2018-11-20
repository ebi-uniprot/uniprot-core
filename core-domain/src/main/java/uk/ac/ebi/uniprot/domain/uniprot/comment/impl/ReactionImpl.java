package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReactionImpl implements Reaction {

	private final String name;
	private final List<DBCrossReference<ReactionReferenceType>> reactionReferences;
	private final ECNumber ecNumber;
	private final List<Evidence> evidences;

	@JsonCreator
	public ReactionImpl(@JsonProperty("name") String name,
			@JsonProperty("reactionReferences") List<DBCrossReference<ReactionReferenceType>> reactionReferences,
			@JsonProperty("ecNumber") ECNumber ecNumber, @JsonProperty("evidences") List<Evidence> evidences) {
		this.name = name;
		if ((reactionReferences == null) || reactionReferences.isEmpty()) {
			this.reactionReferences = Collections.emptyList();
		} else {
			this.reactionReferences = Collections.unmodifiableList(reactionReferences);
		}
		this.ecNumber = ecNumber;
		if ((evidences == null) || evidences.isEmpty()) {
			this.evidences = Collections.emptyList();
		} else {
			this.evidences = Collections.unmodifiableList(evidences);
		}
	}

	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<DBCrossReference<ReactionReferenceType>> getReactionReferences() {
		return reactionReferences;
	}

	@Override
	public ECNumber getEcNumber() {
		return ecNumber;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Reaction=").append(name).append(";");
		sb.append(" Xref=")
				.append(reactionReferences.stream().map(this::getReferenceString).collect(Collectors.joining(", ")))
				.append(";");
		if ((ecNumber != null) && ecNumber.isValid()) {
			sb.append(" EC=").append(ecNumber.getValue()).append(";");
		}
		if (!evidences.isEmpty()) {
			sb.append(" Evidence={")
					.append(evidences.stream().map(val -> val.toString()).collect(Collectors.joining(", ")))
					.append("};");
		}

		return sb.toString();
	}

	private String getReferenceString(DBCrossReference<ReactionReferenceType> ref) {
		return ref.getDatabaseType().getName() + ":" + ref.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ecNumber == null) ? 0 : ecNumber.hashCode());
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reactionReferences == null) ? 0 : reactionReferences.hashCode());
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
		ReactionImpl other = (ReactionImpl) obj;
		if (ecNumber == null) {
			if (other.ecNumber != null)
				return false;
		} else if (!ecNumber.equals(other.ecNumber))
			return false;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reactionReferences == null) {
			if (other.reactionReferences != null)
				return false;
		} else if (!reactionReferences.equals(other.reactionReferences))
			return false;
		return true;
	}

}
