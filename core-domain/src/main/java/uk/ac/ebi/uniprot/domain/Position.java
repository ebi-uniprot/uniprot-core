package uk.ac.ebi.uniprot.domain;

public   final class  Position implements Comparable<Position> {
	private Integer value;
	private PositionModifier modifier;

	private Position(){

	}

	public Position(Integer value) {
		this(value, getValueModifier(value));
	
	}


	public Position(Integer value, PositionModifier modifier) {
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
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	

}
