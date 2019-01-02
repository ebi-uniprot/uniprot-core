package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

public interface EntryInactiveReason {
    InactiveReasonType getInactiveReasonType();

    List<String> getMergeDemergeTo();
}
