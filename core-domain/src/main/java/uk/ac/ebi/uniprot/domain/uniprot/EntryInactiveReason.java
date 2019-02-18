package uk.ac.ebi.uniprot.domain.uniprot;

import java.io.Serializable;
import java.util.List;

public interface EntryInactiveReason extends Serializable {
    InactiveReasonType getInactiveReasonType();

    List<String> getMergeDemergeTo();
}
