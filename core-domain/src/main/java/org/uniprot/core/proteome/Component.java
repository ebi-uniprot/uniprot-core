package org.uniprot.core.proteome;

import org.uniprot.core.CrossReference;

import java.io.Serializable;
import java.util.List;

public interface Component extends Serializable {
    String getName();

    String getDescription();

    List<CrossReference<ProteomeDatabase>> getProteomeCrossReferences();

    int getProteinCount();

    ComponentType getType();
}
