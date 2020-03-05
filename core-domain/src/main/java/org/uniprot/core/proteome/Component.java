package org.uniprot.core.proteome;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.CrossReference;

public interface Component extends Serializable {
    String getName();

    String getDescription();

    List<CrossReference<ProteomeDatabase>> getProteomeCrossReferences();

    int getProteinCount();

    ComponentType getType();
}
