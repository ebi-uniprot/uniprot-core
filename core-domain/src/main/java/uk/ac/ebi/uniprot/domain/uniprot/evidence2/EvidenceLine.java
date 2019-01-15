package uk.ac.ebi.uniprot.domain.uniprot.evidence2;

import java.time.LocalDate;

public interface EvidenceLine {
    String getEvidence();

    LocalDate getCreateDate();

    String getCurator();

    Evidence toEvidence();
}
