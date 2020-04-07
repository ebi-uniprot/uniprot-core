package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

/** @author sahmad */
public interface Fusion extends Serializable {
    List<String> getCters();

    List<String> getNters();
}
