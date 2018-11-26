package uk.ac.ebi.uniprot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Range {
	private final Position start;
	private final Position end;
	public static Range create(Integer start, Integer end) {
		return new Range(start, end);
	}
	
	public Range (Integer start, Integer end) {
		this(new Position(start), new Position(end));
	}
	
	public Range (Integer start, Integer end, PositionModifier sModifier, PositionModifier eModifier) {
		
		this(new Position(start, sModifier), new Position(end, eModifier));
	}
	@JsonCreator
	public Range(@JsonProperty("start")  Position start, 
			@JsonProperty("end")  Position end) {
		this.start = start;
		this.end =end;
	}
	public Position getStart() {
		return start;
	}
	public Position getEnd() {
		return end;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Range other = (Range) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
	
}
