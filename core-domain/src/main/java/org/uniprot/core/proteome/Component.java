package org.uniprot.core.proteome;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.DBCrossReference;

public interface Component extends Serializable {
    String getName();

    String getDescription();

    List<DBCrossReference<ProteomeXReferenceType>> getDbXReferences();

    int getProteinCount();

    ComponentType getType();
}
