package org.uniprot.core.uniparc;

import java.io.Serializable;

public interface ProteomeIdComponent extends Serializable {
    String getProteomeId();

    String getComponent();
}
