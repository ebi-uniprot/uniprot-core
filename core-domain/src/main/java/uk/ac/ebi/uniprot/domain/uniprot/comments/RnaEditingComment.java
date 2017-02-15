package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;


/**
 * CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
 */
public interface RnaEditingComment  extends Comment {

    public final static String POSITIONS_PREFIX = "Modified_positions=";

    /**
     * CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
     *
     * @return Text after Note=
     */

    public Note getRnaEditingNote();

    /** CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
     *
     * @return list of positions x[,y,z]
     */


    /** CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
     *
     * @return if the location type is known or some other
     */
    public RnaEditingLocationType getLocationType();

    public List<Position> getPositionsWithEvidences();

}
