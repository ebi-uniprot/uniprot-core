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
    private final List<Evidence> evidences ;

    @JsonCreator
    public UniProtDBCrossReferenceImpl(
    		@JsonProperty("database") UniProtXDbType database,
    		@JsonProperty("id") String id,        
    		@JsonProperty("properties") List<Property> properties,
            @JsonProperty("isoformId") String isoformId, 
            @JsonProperty("evidences")List<Evidence> evidences) {
    	super(database, id, properties);
    	
    	this.isoformId = isoformId;
    	if((evidences ==null) || evidences.isEmpty())
			this.evidences =Collections.emptyList();
		else {
			this.evidences = new ArrayList<>();
			this.evidences.addAll(evidences);
		}
    }
  
 
    public String getIsoformId() {
		return isoformId;
	}

	public List<Evidence> getEvidences() {
		return evidences;
	}

	private String getDatabaseName() {
		return getDatabaseType().getDisplayName();
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

	

  
   
	
}
