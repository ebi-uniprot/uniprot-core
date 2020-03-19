package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

public interface Interaction extends Serializable {
	Interactant getFirstInteractant();
	Interactant getSecondInteractant();
    int getNumberOfExperiments();
    boolean isOrganismsDiffer();
}
