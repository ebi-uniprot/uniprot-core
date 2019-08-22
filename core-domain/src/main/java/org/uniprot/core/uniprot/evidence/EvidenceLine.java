package org.uniprot.core.uniprot.evidence;

import java.io.Serializable;
import java.time.LocalDate;

public interface EvidenceLine  extends Serializable{
    String getEvidence();

    LocalDate getCreateDate();

    String getCurator();

    Evidence toEvidence();
}