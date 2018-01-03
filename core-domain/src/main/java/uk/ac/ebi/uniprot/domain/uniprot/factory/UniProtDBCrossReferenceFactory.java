package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDatabaseCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDatabaseCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDatabaseCrossReferencesImpl;

import java.util.List;

public enum UniProtDBCrossReferenceFactory {
    INSTANCE;

    public UniProtDatabaseCrossReference createUniProtDatabaseCrossReference(DatabaseType type,
            String id, String description){
        return createUniProtDatabaseCrossReference(type, id, description, null);  
    }
    public UniProtDatabaseCrossReference createUniProtDatabaseCrossReference(DatabaseType type,
            String id, String description, String thirdAttribute){
        return createUniProtDatabaseCrossReference(type, id, description, thirdAttribute, null, null); 
    }
    
    public UniProtDatabaseCrossReference createUniProtDatabaseCrossReference(DatabaseType type,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId){
        return createUniProtDatabaseCrossReference(type, id, description, thirdAttribute, fourthAttribute, isoformId, null);
    }
    
    public UniProtDatabaseCrossReference createUniProtDatabaseCrossReference(DatabaseType type,
            String id, String description, String thirdAttribute,
            String fourthAttribute, String isoformId, List<Evidence> evidences){
        return new  UniProtDatabaseCrossReferenceImpl( type,
                 id,  description,  thirdAttribute,
                 fourthAttribute,  isoformId,  evidences);
    }
    
    public UniProtDatabaseCrossReferences createUniProtDatabaseCrossReferences(List<UniProtDatabaseCrossReference> xrefs) {
        return new UniProtDatabaseCrossReferencesImpl(xrefs);
    }
 
}
