package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Absorption extends HasEvidences {
     int getMax();
     boolean isApproximate();
     Note getNote();
  

}
