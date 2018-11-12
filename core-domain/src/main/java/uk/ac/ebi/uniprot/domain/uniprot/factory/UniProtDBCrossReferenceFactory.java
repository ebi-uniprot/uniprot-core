package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypes;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

public enum UniProtDBCrossReferenceFactory {
    INSTANCE;

    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
            String id, String description){
        return createUniProtDBCrossReference(type, id, description, null);  
    }
    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
            String id, String description, String thirdAttribute){
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, null); 
    }
    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
            String id, String description, String thirdAttribute, String fourthAttribute){
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, fourthAttribute, null); 
    }
    
    
    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId){
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, fourthAttribute, isoformId, null);
    }
    
    public UniProtDBCrossReference createUniProtDBCrossReference(String databaseName,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId, List<Evidence> evidences){
    	Optional<UniProtXDbType> opType =UniProtXDbTypes.INSTANCE.getType(databaseName);
    	List<Property> properties = new ArrayList<>();
    	if(opType.isPresent()) {
    		
    		List<DBXRefTypeAttribute> attributes = opType.get().getAttributes();    	
   			addProperty(properties, attributes, 0, description);
   			addProperty(properties, attributes, 1, thirdAttribute);
   			addProperty(properties, attributes, 2, fourthAttribute);
    	}
        return new  UniProtDBCrossReferenceImpl( databaseName,
                 id, properties,  isoformId,  evidences);
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
    
    public UniProtDBCrossReferences createUniProtDBCrossReferences(List<UniProtDBCrossReference> xrefs) {
    	if((xrefs ==null)|| xrefs.isEmpty()) {
    		return new UniProtDBCrossReferencesImpl(Collections.emptyList());
    	}
        return new UniProtDBCrossReferencesImpl(xrefs.stream()
        		.map(val -> (UniProtDBCrossReferenceImpl) val)
        		.collect(Collectors.toList()));
    }
   
 
}
