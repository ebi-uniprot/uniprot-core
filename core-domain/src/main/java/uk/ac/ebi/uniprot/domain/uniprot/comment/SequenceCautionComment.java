package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence2.HasEvidences;

import java.util.List;


public interface SequenceCautionComment extends Comment, HasEvidences {

    String getSequence();

    String getNote();

    SequenceCautionType getSequenceCautionType();

    List<String> getPositions();
}
