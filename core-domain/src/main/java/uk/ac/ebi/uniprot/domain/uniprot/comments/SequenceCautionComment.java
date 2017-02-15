package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

/**
 * User: Emilio Salazar Date: 14-May-2007
 */
public interface SequenceCautionComment extends Comment {

    public String getSequence();

    public boolean hasNote();

    public SequenceCautionCommentNote getNote();

    public SequenceCautionType getType();

    public List<SequenceCautionPosition> getPositions();
}
