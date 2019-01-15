package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import uk.ac.ebi.uniprot.domain.Range;

public interface MassSpectrometryRange {
    Range getRange();

    boolean hasIsoformId();

    String getIsoformId();
}
