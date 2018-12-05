package uk.ac.ebi.uniprot.domain.uniprot.xdb;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl.class, name = "UniProtDBCrossReferences")
})
public interface UniProtDBCrossReferences {
    List< UniProtDBCrossReference> getCrossReferences();
    List<UniProtDBCrossReference >getCrossReferencesByType(UniProtXDbType type);
    List<UniProtDBCrossReference >getCrossReferencesByType(String dbName);
}
