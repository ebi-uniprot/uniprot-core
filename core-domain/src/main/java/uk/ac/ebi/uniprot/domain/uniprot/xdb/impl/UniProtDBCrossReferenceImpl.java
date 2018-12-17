package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UniProtDBCrossReferenceImpl extends DBCrossReferenceImpl<UniProtXDbType> implements UniProtDBCrossReference {

    private static final String SEMICOLON = "; ";
	private static final String DASH = "-";
    private final String isoformId;
 
    @JsonCreator
    public UniProtDBCrossReferenceImpl(
    		@JsonProperty("database") UniProtXDbType database,
    		@JsonProperty("id") String id,        
    		@JsonProperty("properties") List<Property> properties,
            @JsonProperty("isoformId") String isoformId) {
    	super(database, id, properties);
    	
    	this.isoformId = isoformId;
    	
    }
  
 
    public String getIsoformId() {
		return isoformId;
	}

	private String getDatabaseName() {
		return getDatabaseType().getName();
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDatabaseName()).append(SEMICOLON);
        sb.append(getId()).append(SEMICOLON);
        if(this.getProperties().isEmpty()) {
        	sb.append(DASH);
        }else {
        	sb.append(
        	this.getProperties().stream()
        	.map(val ->val.getValue())
        	.collect(Collectors.joining(SEMICOLON)));
        }
        sb.append(".");

        if (!Strings.isNullOrEmpty(isoformId )) {
            sb.append(" [").append(isoformId).append("]");
        }
        return sb.toString();
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((isoformId == null) ? 0 : isoformId.hashCode());
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
		UniProtDBCrossReferenceImpl other = (UniProtDBCrossReferenceImpl) obj;
		if (isoformId == null) {
			if (other.isoformId != null)
				return false;
		} else if (!isoformId.equals(other.isoformId))
			return false;
		return true;
	}

	

  
   
	
}
