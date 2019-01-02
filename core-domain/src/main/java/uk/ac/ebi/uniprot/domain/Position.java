package uk.ac.ebi.uniprot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public   final class  Position implements Comparable<Position> {
	private final Integer value;
	private final PositionModifier modifier;
	
	public Position(Integer value) {
		this(value, getValueModifier(value));
	
	}

	@JsonCreator
	public Position(@JsonProperty("value") Integer value, 
			@JsonProperty("modifier")PositionModifier modifier) {
		this.value = value;
		if(value ==null) {
			this.modifier =PositionModifier.UNKOWN;
		}else
		
			this.modifier = modifier;
	}
	private static PositionModifier getValueModifier(Integer value) {
			PositionModifier modifier = PositionModifier.EXACT;
			if(value ==null) {
				modifier = PositionModifier.UNKOWN;			
			}else if(value.intValue()<0) {
				modifier = PositionModifier.UNKOWN;		
			}
			return modifier;
	}
	public Integer getValue() {
		return value;
	}
	public PositionModifier getModifier() {
		return modifier;
	}
	@Override
	public int compareTo(Position o) {
		if(modifier == PositionModifier.UNKOWN) {
			if(o.getModifier() == PositionModifier.UNKOWN) {
				return -1;
			}else {
				return 1;
			}
		}else if(o.getModifier() == PositionModifier.UNKOWN) {
			return -1;
		}else {
			return this.getValue().compareTo(o.getValue());
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if(PositionModifier.UNKOWN == modifier) {
			return  prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		}
		result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Position other = (Position) obj;
		if (modifier != other.modifier)
			return false;
		if(PositionModifier.UNKOWN == modifier)
			return true;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	

}
