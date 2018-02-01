package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;

public class FlagImpl implements Flag {
    private final FlagType type;
    public FlagImpl(FlagType type){
        this.type = type;
      
    }


    @Override
    public FlagType getFlagType() {
        return type;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		FlagImpl other = (FlagImpl) obj;
		if (type != other.type)
			return false;
		return true;
	}
  
}
