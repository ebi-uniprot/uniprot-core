package uk.ac.ebi.uniprot.domain.xdb;

public interface DBCrossReference<T extends DatabaseName> {
    T getDatabase();
    String getId();
    String getDescription();
    
}
