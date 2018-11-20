package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SequenceCautionCommentImpl extends CommentImpl implements SequenceCautionComment {
    private final SequenceCautionType sequenceCautionType;
    private final String sequence;
    private final List<String> positions;
    private final String note;
    private final List<Evidence> evidences;
    
	@JsonCreator
    public SequenceCautionCommentImpl(@JsonProperty("sequenceCautionType") SequenceCautionType sequenceCautionType, 
    		@JsonProperty("sequence") String sequence,
    		@JsonProperty("positions") List<String> positions,
    		@JsonProperty("note")String note,
    		@JsonProperty("evidences") List<Evidence> evidences) {
        super(CommentType.SEQUENCE_CAUTION);
        this.sequenceCautionType = sequenceCautionType;
        this.sequence =sequence;
        if ((positions == null) || positions.isEmpty()) {
            this.positions = Collections.emptyList();
        } else {
            this.positions = Collections.unmodifiableList(positions);
        }
        this.note = note ;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public SequenceCautionType getSequenceCautionType() {
        return sequenceCautionType;
    }

    @Override
    public List<String> getPositions() {
        return positions;
    }

	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((sequenceCautionType == null) ? 0 : sequenceCautionType.hashCode());
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
		SequenceCautionCommentImpl other = (SequenceCautionCommentImpl) obj;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (sequenceCautionType != other.sequenceCautionType)
			return false;
		return true;
	}

}
