package uk.ac.ebi.uniprot.domain.xdb;

public interface DatabaseCrossReference<T extends DatabaseName> {
    T getDatabase();
    String getId();
    String getDescription();
    
}
