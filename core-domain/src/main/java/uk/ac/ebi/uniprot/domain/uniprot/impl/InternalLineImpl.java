package uk.ac.ebi.uniprot.domain.uniprot.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InternalLineImpl extends ValueImpl implements InternalLine {
	 private final InternalLineType type;
	 @JsonCreator
     public InternalLineImpl(@JsonProperty("type") InternalLineType type,
    		 @JsonProperty("value") String value) {
         super(value);
         this.type = type;
     }
	@Override
	public InternalLineType getType() {
		return type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		InternalLineImpl other = (InternalLineImpl) obj;
		if (type != other.type)
			return false;
		return true;
	}

   
}
