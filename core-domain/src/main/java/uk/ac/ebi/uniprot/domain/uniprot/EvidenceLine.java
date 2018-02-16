package uk.ac.ebi.uniprot.domain.uniprot;

import java.time.LocalDate;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

public interface EvidenceLine {
	Evidence getEvidence();
	LocalDate getCreateDate();
	String getCurator();
}
