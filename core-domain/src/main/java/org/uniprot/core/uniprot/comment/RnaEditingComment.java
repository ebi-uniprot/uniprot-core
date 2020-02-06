package org.uniprot.core.uniprot.comment;

import java.util.List;

/** CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=]. */
public interface RnaEditingComment extends Comment, HasMolecule {

    String POSITIONS_PREFIX = "Modified_positions=";

    RnaEditingLocationType getLocationType();

    List<RnaEdPosition> getPositions();

    Note getNote();

    boolean hasLocationType();

    boolean hasPositions();

    boolean hasNote();
}
