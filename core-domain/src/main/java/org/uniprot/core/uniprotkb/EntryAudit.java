package org.uniprot.core.uniprotkb;

import java.io.Serializable;
import java.time.LocalDate;

public interface EntryAudit extends Serializable {

    LocalDate getFirstPublicDate();

    LocalDate getLastAnnotationUpdateDate();

    LocalDate getLastSequenceUpdateDate();

    int getEntryVersion();

    int getSequenceVersion();
}
