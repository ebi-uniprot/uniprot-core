package org.uniprot.core;

import java.io.Serializable;

public interface Value extends Serializable {
    String getValue();

    boolean hasValue();
}
