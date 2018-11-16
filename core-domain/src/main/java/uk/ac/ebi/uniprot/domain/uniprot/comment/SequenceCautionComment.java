package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface SequenceCautionComment extends Comment, HasEvidences {

     String getSequence();
     
     Optional<String> getNote();

     SequenceCautionType getSequenceCautionType();

     List<String> getPositions();
}
