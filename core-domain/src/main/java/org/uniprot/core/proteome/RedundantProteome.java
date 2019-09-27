package org.uniprot.core.proteome;

import java.io.Serializable;

public interface RedundantProteome extends Serializable {
    ProteomeId getId();

    Float getSimilarity();
}
