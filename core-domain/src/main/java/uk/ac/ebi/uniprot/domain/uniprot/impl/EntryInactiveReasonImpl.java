package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.InactiveReasonType;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EntryInactiveReasonImpl implements EntryInactiveReason {
	private final InactiveReasonType inactiveReasonType;
	private final List<String> mergeDemergeTo;
	public EntryInactiveReasonImpl(
			@JsonProperty("inactiveReasonType") InactiveReasonType inactiveReasonType,
			@JsonProperty("mergeDemergeTo") List<String> mergeDemergeTo) {
		this.inactiveReasonType = inactiveReasonType;
		this.mergeDemergeTo = Utils.unmodifierList(mergeDemergeTo);
	}
	@Override
	public InactiveReasonType getInactiveReasonType() {
		return inactiveReasonType;
	}

	@Override
	public List<String> getMergeDemergeTo() {
		return mergeDemergeTo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inactiveReasonType == null) ? 0 : inactiveReasonType.hashCode());
		result = prime * result + ((mergeDemergeTo == null) ? 0 : mergeDemergeTo.hashCode());
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
		EntryInactiveReasonImpl other = (EntryInactiveReasonImpl) obj;
		if (inactiveReasonType != other.inactiveReasonType)
			return false;
		if (mergeDemergeTo == null) {
			if (other.mergeDemergeTo != null)
				return false;
		} else if (!mergeDemergeTo.equals(other.mergeDemergeTo))
			return false;
		return true;
	}

}
