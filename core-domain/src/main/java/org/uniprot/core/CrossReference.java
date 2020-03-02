package org.uniprot.core;

import java.io.Serializable;
import java.util.List;

public interface CrossReference<T extends Database> extends Serializable {
    T getDatabase();

    String getId();

    List<Property> getProperties();

    boolean hasDatabase();

    boolean hasId();

    boolean hasProperties();
}
