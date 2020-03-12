package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtkbAccession;

public interface Interaction extends Serializable {
    InteractionType getType();

    UniProtkbAccession getUniProtkbAccession();

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
