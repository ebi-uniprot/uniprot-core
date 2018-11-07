package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.common.Property;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypes;
import uk.ac.ebi.uniprot.domain.xdb.impl.DBCrossReferenceImpl;

public class UniProtDBCrossReferenceImpl extends DBCrossReferenceImpl implements UniProtDBCrossReference {

    private static final String SEMICOLON = "; ";
	private static final String DASH = "-";
    private String isoformId;
    private List<Evidence> evidences =Collections.emptyList();

    public UniProtDBCrossReferenceImpl() {
    	
    }
	
    public UniProtDBCrossReferenceImpl(String databaseName,
        String id) {
      super(databaseName, id);
    }
    
    public UniProtDBCrossReferenceImpl(String databaseName,
            String id, String description) {
    	this(databaseName, id, description, null, null, null, Collections.emptyList());
    }
    
    public UniProtDBCrossReferenceImpl(String databaseName,
            String id, String description, String thridAttribute) {
    	this(databaseName, id, description, thridAttribute, null, null, Collections.emptyList());
    }
    
    public UniProtDBCrossReferenceImpl(String databaseName,
            String id, String description, String thirdAttribute,
            String fourthAttribute) {
    	this(databaseName, id, description, thirdAttribute, fourthAttribute, null, Collections.emptyList());
    }
  
    public UniProtDBCrossReferenceImpl(String databaseName,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId) {
    	this(databaseName, id, description, thirdAttribute, fourthAttribute, isoformId, Collections.emptyList());
    }
  
    
    public UniProtDBCrossReferenceImpl(String databaseName,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId, List<Evidence> evidences) {
    	super(databaseName, id);
    	Optional<UniProtXDbType> opType =UniProtXDbTypes.INSTANCE.getType(databaseName);
    	if(opType.isPresent()) {
    		List<Property> properties = new ArrayList<>();
    		List<DBXRefTypeAttribute> attributes = opType.get().getAttributes();    	
   			addProperty(properties, attributes, 0, description);
   			addProperty(properties, attributes, 1, thirdAttribute);
   			addProperty(properties, attributes, 2, fourthAttribute);
   			this.setProperties(properties);
    	}
    	this.isoformId = isoformId;
    	setEvidences(evidences);
    }
  
   private void addProperty(List<Property> properties, List<DBXRefTypeAttribute> attributes, int number, String value) {
    	if(Strings.isNullOrEmpty(value))
    		return ;
    	if(attributes.size()< number+1) {
    		return ;
    	}
    	DBXRefTypeAttribute attr = attributes.get(number);
    	properties.add( new Property(attr.getName(), value));
    }
    public String getIsoformId() {
		return isoformId;
	}

	public void setIsoformId(String isoformId) {
		this.isoformId = isoformId;
	}

	public List<Evidence> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<Evidence> evidences) {
		if((evidences ==null) || evidences.isEmpty()) {
			this.evidences = Collections.emptyList();
		}else {
			this.evidences = new ArrayList<>();
			this.evidences.addAll(evidences);
		}
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
