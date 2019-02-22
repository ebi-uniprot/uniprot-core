package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import java.io.Serializable;

public interface Interaction extends Serializable {
    InteractionType getType();

    UniProtAccession getUniProtAccession();

    String getGeneName();

    int getNumberOfExperiments();

    Interactor getFirstInteractor();

    Interactor getSecondInteractor();


    boolean hasUniProtAccession();

    boolean hasGeneName();

    boolean hasNumberOfExperiments();

    boolean hasFirstInteractor();

    boolean hasSecondInteractor();
}
