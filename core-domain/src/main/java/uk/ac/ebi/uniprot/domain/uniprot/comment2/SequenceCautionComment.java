package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

import java.util.List;


public interface SequenceCautionComment extends Comment, HasEvidences {

    String getSequence();

    String getNote();

    SequenceCautionType getSequenceCautionType();

    List<String> getPositions();
}
