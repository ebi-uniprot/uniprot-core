package org.uniprot.core.uniprot.comment;

import java.io.Serializable;

import org.uniprot.core.uniprot.UniProtAccession;

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
