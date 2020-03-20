package org.uniprot.core.uniprotkb;

import java.io.Serializable;
import java.util.List;

public interface EntryInactiveReason extends Serializable {
    InactiveReasonType getInactiveReasonType();

    List<String> getMergeDemergeTos();
}
