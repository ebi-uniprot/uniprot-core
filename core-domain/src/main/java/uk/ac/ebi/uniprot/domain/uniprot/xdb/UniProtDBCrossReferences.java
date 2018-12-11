package uk.ac.ebi.uniprot.domain.uniprot.xdb;


import java.util.List;

public interface UniProtDBCrossReferences {
    List< UniProtDBCrossReference> getCrossReferences();
    List<UniProtDBCrossReference >getCrossReferencesByType(UniProtXDbType type);
    List<UniProtDBCrossReference >getCrossReferencesByType(String dbName);
}
