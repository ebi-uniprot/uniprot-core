package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Absorption extends HasEvidences {
    public int getMax();
    public boolean isApproximation();
    public AbsorptionNote getNote();
    public boolean hasNote();
  

}
