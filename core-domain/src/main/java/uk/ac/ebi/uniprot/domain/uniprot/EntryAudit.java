package uk.ac.ebi.uniprot.domain.uniprot;

import java.time.LocalDate;

public interface EntryAudit {

    LocalDate getFirstPublicDate();

    LocalDate getLastAnnotationUpdateDate();

    LocalDate getLastSequenceUpdateDate();

    int getEntryVersion();

    int getSequenceVersion();

}
