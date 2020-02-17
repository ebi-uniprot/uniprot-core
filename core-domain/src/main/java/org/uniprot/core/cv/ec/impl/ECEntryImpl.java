package org.uniprot.core.cv.ec.impl;

import org.uniprot.core.cv.ec.ECEntry;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public class ECEntryImpl implements ECEntry, Serializable {
    private static final long serialVersionUID = -5403788164376756075L;
    private final String id;
    private final String label;

    public ECEntryImpl(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECEntryImpl ec = (ECEntryImpl) o;
        return Objects.equals(id, ec.id) && Objects.equals(label, ec.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }
}
