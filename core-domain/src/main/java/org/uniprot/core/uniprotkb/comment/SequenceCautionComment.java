package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface SequenceCautionComment extends Comment, HasEvidences, HasMolecule {

    String getSequence();

    String getNote();

    SequenceCautionType getSequenceCautionType();

    boolean hasSequence();

    boolean hasNote();

    boolean hasSequenceCautionType();
}
