package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

public interface Interaction extends Serializable {
	Interactor getFirstInteractor();
	Interactor getSecondInteractor();
    int getNumberOfExperiments();
    boolean isXeno();
}
