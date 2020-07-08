package org.uniprot.core;

import java.io.Serializable;

import org.uniprot.core.util.Utils;

public interface Value extends Serializable {
    String getValue();

    default boolean hasValue() {
        return Utils.notNullNotEmpty(getValue());
    }
}
