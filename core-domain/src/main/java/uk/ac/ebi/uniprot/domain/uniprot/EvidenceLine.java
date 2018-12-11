package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.time.LocalDate;

public interface EvidenceLine {
	String getEvidence();
	LocalDate getCreateDate();
	String getCurator();
	Evidence toEvidence();
}
