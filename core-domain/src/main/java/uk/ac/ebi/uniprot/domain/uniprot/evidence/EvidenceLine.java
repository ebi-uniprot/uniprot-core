package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import java.time.LocalDate;

public interface EvidenceLine {
    String getEvidence();

    LocalDate getCreateDate();

    String getCurator();

    Evidence toEvidence();
}
