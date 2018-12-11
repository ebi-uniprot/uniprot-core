package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.Range;

public interface MassSpectrometryRange {
	Range getRange();

	boolean hasIsoformId();

	String getIsoformId();
}
