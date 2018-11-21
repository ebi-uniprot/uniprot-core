package uk.ac.ebi.uniprot.domain.citation;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.CitationXrefsImpl.class, name = "CitationXrefsImpl")
})
public interface CitationXrefs {
    List<DBCrossReference<CitationXrefType>> getXrefs();
    Optional<DBCrossReference<CitationXrefType>> getTyped(CitationXrefType type);
}
