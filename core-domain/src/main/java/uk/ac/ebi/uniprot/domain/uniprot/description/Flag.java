package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.io.Serializable;

public interface Flag extends Serializable {
    FlagType getType();
}
