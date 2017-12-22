package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface SequenceCautionComment extends Comment {

     String getSequence();

     boolean hasNote();

     String getNote();

     SequenceCautionType getSequenceCautionType();

     List<String> getPositions();
}
