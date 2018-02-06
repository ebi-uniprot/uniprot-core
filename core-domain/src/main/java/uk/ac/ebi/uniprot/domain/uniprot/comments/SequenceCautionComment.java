package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface SequenceCautionComment extends Comment, HasEvidences {

     String getSequence();

     boolean hasNote();

     String getNote();

     SequenceCautionType getSequenceCautionType();

     List<String> getPositions();
}
