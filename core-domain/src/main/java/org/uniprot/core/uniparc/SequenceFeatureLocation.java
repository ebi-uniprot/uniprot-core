package org.uniprot.core.uniparc;

import java.io.Serializable;

public interface SequenceFeatureLocation extends Serializable {
    int getStart();

    int getEnd();

    String getAlignment();
}
