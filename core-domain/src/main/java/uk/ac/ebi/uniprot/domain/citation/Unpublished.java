package uk.ac.ebi.uniprot.domain.citation;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * For unpublished observations the format of the RL line is:
 *
 * RL   Unpublished observations (MMM-YYYY).
 * Where 'MMM' is the month and 'YYYY' is the year.
 *
 * We use the 'unpublished observations' RL line to cite communications by scientists
 * to Swiss-Prot of unpublished information concerning various aspects of a sequence entry.
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl.class, name = "UnpublishedImpl")
})
public interface Unpublished extends Citation {

}
