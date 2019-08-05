package org.uniprot.core.uniprot.comment;


import java.util.List;

import org.uniprot.core.uniprot.evidence.HasEvidences;


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
