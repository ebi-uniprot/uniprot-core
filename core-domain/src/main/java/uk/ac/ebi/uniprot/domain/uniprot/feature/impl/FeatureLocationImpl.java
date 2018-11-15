package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureLocationModifier;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeatureLocationImpl implements FeatureLocation {
	private final Integer start;
	private final Integer end;
	private final FeatureLocationModifier startModifier;
	private final FeatureLocationModifier endModifier;

	public FeatureLocationImpl(Integer start, Integer end) {
		this(start, end, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
	}
	@JsonCreator
	public FeatureLocationImpl(
			@JsonProperty("start") Integer start, 
			@JsonProperty("end") Integer end, 
			@JsonProperty("startModifier") FeatureLocationModifier startModifier,
			@JsonProperty("endModifier") FeatureLocationModifier endModifier) {
		this.start = start;
		this.end = end;
		if (startModifier == null)
			this.startModifier = FeatureLocationModifier.EXACT;
		else
			this.startModifier = startModifier;
		if (endModifier == null)
			this.endModifier = FeatureLocationModifier.EXACT;
		else
			this.endModifier = endModifier;
		if ((this.start == null) && (this.startModifier == FeatureLocationModifier.EXACT)) {
			throw new IllegalArgumentException("Start location is not properly set.");
		}
		if ((this.end == null) && (this.endModifier == FeatureLocationModifier.EXACT)) {
			throw new IllegalArgumentException("End location is not properly set.");
		}
	}

	@Override
	public FeatureLocationModifier getStartModifier() {
		return startModifier;
	}

	@Override
	public Integer getStart() {
		return start;
	}
	@JsonIgnore
	@Override
	public boolean isStartAvailable() {
		return (start != null) || startModifier == FeatureLocationModifier.UNKOWN;
	}

	@Override
	public Integer getEnd() {
		return end;
	}

	@Override
	public FeatureLocationModifier getEndModifier() {
		return endModifier;
	}
	@JsonIgnore
	@Override
	public boolean isEndAvailable() {
		return end != null || endModifier == FeatureLocationModifier.UNKOWN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((endModifier == null) ? 0 : endModifier.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((startModifier == null) ? 0 : startModifier.hashCode());
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
		FeatureLocationImpl other = (FeatureLocationImpl) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (endModifier != other.endModifier)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (startModifier != other.startModifier)
			return false;
		return true;
	}

}
