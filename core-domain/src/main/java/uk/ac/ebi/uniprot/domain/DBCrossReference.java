package uk.ac.ebi.uniprot.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl.class, name = "dbxref")
})

public interface DBCrossReference {
	String getDatabaseName();
    String getId();
    List<Property> getProperties();
}
