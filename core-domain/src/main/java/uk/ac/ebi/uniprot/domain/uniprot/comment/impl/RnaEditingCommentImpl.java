package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RnaEditingCommentImpl extends CommentImpl implements RnaEditingComment {
	public static RnaEdPosition createPosition(String position, List<Evidence> evidences) {
		return new RnaEdPositionImpl(position, evidences);
	}

	private final RnaEditingLocationType locationType;
	private final List<RnaEdPosition> positions;
	private final Note note;

	@JsonCreator
	public RnaEditingCommentImpl(@JsonProperty("locationType") RnaEditingLocationType locationType,
			@JsonProperty("positions") List<RnaEdPosition> positions, @JsonProperty("note") Note note) {
		super(CommentType.RNA_EDITING);
		this.locationType = locationType;
		if ((positions == null) || positions.isEmpty()) {
			this.positions = Collections.emptyList();
		} else {
			this.positions = Collections.unmodifiableList(positions);
		}
		this.note = note;
	}

	@Override
	public RnaEditingLocationType getLocationType() {
		return locationType;
	}

	@Override
	public List<RnaEdPosition> getPositions() {
		return positions;
	}

	@Override
	public Note getNote() {
		return note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RnaEditingCommentImpl other = (RnaEditingCommentImpl) obj;
		if (locationType != other.locationType)
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		return true;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class RnaEdPositionImpl implements RnaEdPosition {
		private final String position;
		private final List<Evidence> evidences;

		@JsonCreator
		public RnaEdPositionImpl(@JsonProperty("position") String position,
				@JsonProperty("evidences") List<Evidence> evidences) {
			this.position = position;
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
		public String getPosition() {
			return position;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
			result = prime * result + ((position == null) ? 0 : position.hashCode());
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
			RnaEdPositionImpl other = (RnaEdPositionImpl) obj;
			if (evidences == null) {
				if (other.evidences != null)
					return false;
			} else if (!evidences.equals(other.evidences))
				return false;
			if (position == null) {
				if (other.position != null)
					return false;
			} else if (!position.equals(other.position))
				return false;
			return true;
		}

	}
}
