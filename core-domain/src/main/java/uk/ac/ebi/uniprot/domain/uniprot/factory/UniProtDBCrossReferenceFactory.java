package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl;

import java.util.List;

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
    
    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId, List<Evidence> evidences){
        return new  UniProtDBCrossReferenceImpl( type,
                 id,  description,  thirdAttribute,
                 fourthAttribute,  isoformId,  evidences);
    }
    
    public UniProtDBCrossReferences createUniProtDBCrossReferences(List<UniProtDBCrossReference> xrefs) {
        return new UniProtDBCrossReferencesImpl(xrefs);
    }
    
    
 
}
