package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.interfaces.HasEvidences;

public interface Absorption extends HasEvidences {
    public int getMax();

    public String getNote();

    public boolean hasNote();

    public boolean isApproximation();

}
