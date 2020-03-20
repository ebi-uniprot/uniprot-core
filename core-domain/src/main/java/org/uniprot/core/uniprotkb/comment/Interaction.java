package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

public interface Interaction extends Serializable {
	Interactant getInteractantOne();
	Interactant getInteractantTwo();
    int getNumberOfExperiments();
    boolean isOrganismsDiffer();
}
