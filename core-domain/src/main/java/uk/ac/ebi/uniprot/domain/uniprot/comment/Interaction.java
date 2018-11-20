package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionImpl.class, name = "InteractionImpl")
})
public interface Interaction {

    public InteractionType getType();

    public UniProtAccession getUniProtAccession();

    public String getGeneName();

    public int getNumberOfExperiments();

    public Interactor getFirstInteractor();

    public Interactor getSecondInteractor();
}
