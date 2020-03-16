package org.uniprot.core.uniprotkb.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinRecName extends Serializable {
    Name getFullName();

    List<Name> getShortNames();

    List<EC> getEcNumbers();

    boolean hasFullName();

    boolean hasShortNames();

    boolean hasEcNumbers();

    boolean isValid();
}
