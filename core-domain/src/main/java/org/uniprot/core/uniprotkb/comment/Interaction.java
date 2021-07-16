package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

import org.uniprot.core.util.Utils;

public interface Interaction extends Serializable {
    Interactant getInteractantOne();

    Interactant getInteractantTwo();

    int getNumberOfExperiments();

    boolean isOrganismDiffer();

    default boolean hasInteractantOne() {
        return Utils.notNull(getInteractantOne());
    }

    default boolean hasInteractantTwo() {
        return Utils.notNull(getInteractantTwo());
    }

    default boolean hasNumberOfExperiments() {
        return getNumberOfExperiments() > 0;
    }
}
