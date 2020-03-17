package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

public interface Interaction extends Serializable {
    InteractionType getType();

    UniProtKBAccession getUniProtkbAccession();

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
