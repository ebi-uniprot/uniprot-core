package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.Range;

import java.io.Serializable;

public interface MassSpectrometryRange extends Serializable {
    Range getRange();

    boolean hasIsoformId();

    String getIsoformId();
}
