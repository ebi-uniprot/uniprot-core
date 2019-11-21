package org.uniprot.core.uniprot.comment;

import java.util.List;

/** CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=]. */
public interface RnaEditingComment extends Comment, HasMolecule {

    public static final String POSITIONS_PREFIX = "Modified_positions=";

    public RnaEditingLocationType getLocationType();

    public List<RnaEdPosition> getPositions();

    public Note getNote();

    boolean hasLocationType();

    boolean hasPositions();

    boolean hasNote();
}
