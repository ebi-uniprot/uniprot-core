package org.uniprot.core.uniprotkb.feature;

import java.io.Serializable;

public interface LigandPart extends Serializable {
    String getName();

    String getId();

    String getLabel();

    String getNote();
}
