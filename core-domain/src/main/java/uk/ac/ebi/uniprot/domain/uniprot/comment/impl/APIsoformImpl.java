package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIsoformImpl implements APIsoform {

	public static IsoformName createIsoformName(String value, List<Evidence> evidences) {
		return new IsoformNameImpl(value, evidences);
	}

	public static IsoformId createIsoformId(String value) {
		return new IsoformIdImpl(value);
	}

	private final IsoformName name;
	private final List<IsoformName> synonyms;
	private final Note note;
	private final List<IsoformId> isoformIds;
	private final List<String> sequenceIds;
	private final IsoformSequenceStatus isoformSequenceStatus;
	@JsonCreator
	public APIsoformImpl(@JsonProperty("name") IsoformName name, 
			@JsonProperty("synonyms")List<IsoformName> synonyms, 
			@JsonProperty("note")Note note, 
			@JsonProperty("isoformIds")List<IsoformId> isoformIds,
			@JsonProperty("sequenceIds") List<String> sequenceIds, 
			@JsonProperty("value")IsoformSequenceStatus isoformSequenceStatus) {
		this.name = name;
		this.synonyms =copyList(synonyms);
		this.note = note;
		this.isoformIds = copyList(isoformIds);
		this.sequenceIds = copyList(sequenceIds);
		
		if (isoformSequenceStatus == null) {
			this.isoformSequenceStatus = IsoformSequenceStatus.DESCRIBED;
		} else
			this.isoformSequenceStatus = isoformSequenceStatus;
	}
	private <T> List<T> copyList(List<T> value){
		if ((value == null) || value.isEmpty()) {
			return  Collections.emptyList();
		} else {
			return  Collections.unmodifiableList(value);
		}
	}
	@Override
	public IsoformName getName() {
		return name;
	}

	@Override
	public List<IsoformName> getSynonyms() {
		return synonyms;
	}

	@Override
	public Note getNote() {
		return note;
	}

	@Override
	public List<IsoformId> getIsoformIds() {
		return isoformIds;
	}

	@Override
	public List<String> getSequenceIds() {
		return sequenceIds;
	}

	@Override
	public IsoformSequenceStatus getIsoformSequenceStatus() {
		return isoformSequenceStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isoformIds == null) ? 0 : isoformIds.hashCode());
		result = prime * result + ((isoformSequenceStatus == null) ? 0 : isoformSequenceStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((sequenceIds == null) ? 0 : sequenceIds.hashCode());
		result = prime * result + ((synonyms == null) ? 0 : synonyms.hashCode());
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
		APIsoformImpl other = (APIsoformImpl) obj;
		if (isoformIds == null) {
			if (other.isoformIds != null)
				return false;
		} else if (!isoformIds.equals(other.isoformIds))
			return false;
		if (isoformSequenceStatus != other.isoformSequenceStatus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (sequenceIds == null) {
			if (other.sequenceIds != null)
				return false;
		} else if (!sequenceIds.equals(other.sequenceIds))
			return false;
		if (synonyms == null) {
			if (other.synonyms != null)
				return false;
		} else if (!synonyms.equals(other.synonyms))
			return false;
		return true;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class IsoformNameImpl extends EvidencedValueImpl implements IsoformName {
		@JsonCreator
		public IsoformNameImpl(@JsonProperty("value") String value,
				@JsonProperty("evidences") List<Evidence> evidences) {
			super(value, evidences);
		}
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class IsoformIdImpl extends ValueImpl implements IsoformId {
		@JsonCreator
		public IsoformIdImpl(@JsonProperty("value") String value) {
			super(value);

		}

	}

}
