package uk.ac.ebi.uniprot.domain;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType.class, name = "DefaultDatabaseType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType.class, name = "UniProtXDbType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureXDbType.class, name = "FeatureXDbType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType.class, name = "CofactorReferenceType"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType.class, name = "ReactionReferenceType")
})
public interface DatabaseType {
    String getName();
}
