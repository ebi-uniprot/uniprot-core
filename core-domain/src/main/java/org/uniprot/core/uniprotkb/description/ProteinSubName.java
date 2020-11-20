package org.uniprot.core.uniprotkb.description;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.util.Utils;

public interface ProteinSubName extends Serializable {
    Name getFullName();

    List<EC> getEcNumbers();

    boolean isValid();

    default boolean hasFullName() {
        return Utils.notNull(getFullName());
    }

    default boolean hasEcNumbers() {
        return Utils.notNullNotEmpty(getEcNumbers());
    }
}
