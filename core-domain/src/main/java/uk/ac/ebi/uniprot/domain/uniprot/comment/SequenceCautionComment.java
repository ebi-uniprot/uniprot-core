package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

import java.util.List;


public interface SequenceCautionComment extends Comment, HasEvidences {

    String getSequence();

    String getNote();

    SequenceCautionType getSequenceCautionType();

    List<String> getPositions();

    boolean hasSequence();

    boolean hasNote();

    boolean hasSequenceCautionType();

    boolean hasPositions();
}
