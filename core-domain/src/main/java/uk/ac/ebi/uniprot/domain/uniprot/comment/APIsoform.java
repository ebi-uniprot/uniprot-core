package uk.ac.ebi.uniprot.domain.uniprot.comment;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl.class, name = "APIsoformImpl")
})
public interface APIsoform   {

     IsoformName getName();
     List<IsoformName> getSynonyms();
     Note getNote();
     List<IsoformId> getIsoformIds();
     List<String> getSequenceIds();
     IsoformSequenceStatus getIsoformSequenceStatus();

}
