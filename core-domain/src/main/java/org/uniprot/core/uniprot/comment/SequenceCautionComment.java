package org.uniprot.core.uniprot.comment;

import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface SequenceCautionComment extends Comment, HasEvidences, HasMolecule {

    String getSequence();

    String getNote();

    SequenceCautionType getSequenceCautionType();

    boolean hasSequence();

    boolean hasNote();

    boolean hasSequenceCautionType();
}
