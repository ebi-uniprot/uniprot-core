package uk.ac.ebi.uniprot.domain.uniprot;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * @author jieluo
 * @date   18 Jan 2017
 * @time   13:21:10
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl.class, name = "FreeTextImpl")
})
public interface FreeText {
    List<EvidencedValue> getTexts();
}
