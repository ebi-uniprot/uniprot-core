package org.uniprot.core.uniprotkb.interaction.impl;

import org.uniprot.core.uniprotkb.interaction.InteractionEntry;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;

import java.util.List;
import java.util.Objects;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public class InteractionEntryImpl implements InteractionEntry {
    private static final long serialVersionUID = 1386997110427466524L;

    private final List<InteractionMatrixItem> interactionMatrix;

    // no arg constructor for JSON deserialization
    InteractionEntryImpl() {
        this(null);
    }

    InteractionEntryImpl(List<InteractionMatrixItem> interactionMatrix) {
        this.interactionMatrix = interactionMatrix;
    }

    @Override
    public List<InteractionMatrixItem> getInteractionMatrix() {
        return interactionMatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionEntryImpl that = (InteractionEntryImpl) o;
        return Objects.equals(interactionMatrix, that.interactionMatrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interactionMatrix);
    }
}
