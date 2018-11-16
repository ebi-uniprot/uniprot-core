package uk.ac.ebi.uniprot.domain;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType.class, name = "DefaultDatabaseType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType.class, name = "EvidenceType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType.class, name = "UniProtXDbType")
})
public interface DatabaseType {
    String getName();
}
