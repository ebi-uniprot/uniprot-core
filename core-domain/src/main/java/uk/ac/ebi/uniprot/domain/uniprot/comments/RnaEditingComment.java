package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;


/**
 * CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
 */
public interface RnaEditingComment  extends Comment {

    public final static String POSITIONS_PREFIX = "Modified_positions=";
    
    public RnaEditingLocationType getLocationType();

    public List<Position> getPositions();

    public CommentNote getRnaEditingNote();
}
