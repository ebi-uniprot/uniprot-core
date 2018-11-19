package uk.ac.ebi.uniprot.domain.uniprot.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.Value;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValueImpl implements Value {
    private final String value;
    @JsonCreator
    public ValueImpl(@JsonProperty("value") String value){
        this.value = value;
    }

    @Override
    public String getValue() {
       return value;
    }

    @Override
    public String toString(){
        return value;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ValueImpl other = (ValueImpl) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

}
