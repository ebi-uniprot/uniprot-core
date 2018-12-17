package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;


import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniProtDBCrossReferenceImpl extends DBCrossReferenceImpl<UniProtXDbType> implements UniProtDBCrossReference {

    private static final String SEMICOLON = "; ";
	private static final String DASH = "-";
    private String isoformId;
    private List<Evidence> evidences ;

	private UniProtDBCrossReferenceImpl(){
		super(null, "", Collections.emptyList());
		this.evidences = Collections.emptyList();
	}

    public UniProtDBCrossReferenceImpl(UniProtXDbType database, String id, List<Property> properties,
            String isoformId, List<Evidence> evidences) {
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

        if (isoformId != null && !isoformId.isEmpty()) {
            sb.append(" [").append(isoformId).append("]");
        }
        return sb.toString();
    }

	

  
   
	
}
