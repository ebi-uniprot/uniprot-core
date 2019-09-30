package org.uniprot.core;

import java.io.Serializable;
import java.util.List;

public interface DBCrossReference<T extends DatabaseType> extends Serializable {
    T getDatabaseType();

    String getId();

    List<Property> getProperties();

    boolean hasDatabaseType();

    boolean hasId();

    boolean hasProperties();
}
