package uk.ac.ebi.uniprot.domain.uniprot;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.EntryAuditImpl.class, name = "EntryAuditImpl")
})
public interface EntryAudit {

    LocalDate getFirstPublicDate();

    LocalDate getLastAnnotationUpdateDate();

    LocalDate getLastSequenceUpdateDate();

    int getEntryVersion();

    int getSequenceVersion();

}
