package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;
import java.util.Optional;


/**
 * CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
 */
public interface RnaEditingComment  extends Comment {

    public final static String POSITIONS_PREFIX = "Modified_positions=";
    
    public RnaEditingLocationType getLocationType();

    public List<Position> getPositions();

    public Optional<Note> getNote();
}
