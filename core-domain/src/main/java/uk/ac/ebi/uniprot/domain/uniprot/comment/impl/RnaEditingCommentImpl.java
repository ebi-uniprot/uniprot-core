package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RnaEditingCommentImpl extends CommentImpl implements RnaEditingComment {

	private RnaEditingLocationType locationType;
	private List<RnaEdPosition> positions;
	private Note note;

	private RnaEditingCommentImpl(){
		super(CommentType.RNA_EDITING);
		this.positions = Collections.emptyList();
	}

	public RnaEditingCommentImpl(RnaEditingLocationType locationType,
			List<RnaEdPosition> positions,Note note) {
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

	public static RnaEdPosition createPosition(String position, List<Evidence> evidences) {
		return new RnaEdPositionImpl(position, evidences);
	}


	public static class RnaEdPositionImpl implements RnaEdPosition {
		private String position;
		private List<Evidence> evidences;

		private RnaEdPositionImpl(){
			this.evidences = Collections.emptyList();
		}

		public RnaEdPositionImpl(String position, List<Evidence> evidences) {
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
