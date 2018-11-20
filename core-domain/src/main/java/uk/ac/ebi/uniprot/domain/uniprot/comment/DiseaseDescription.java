package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;

/**
 * Generic description of the disease.
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @see Disease
 * @version 1.0
 */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseImpl.DiseaseDescriptionImpl.class, name = "DiseaseDescriptionImpl")
})
public interface DiseaseDescription extends EvidencedValue{
}