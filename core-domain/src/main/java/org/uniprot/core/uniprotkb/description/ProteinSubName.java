package org.uniprot.core.uniprotkb.description;

import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.List;

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
