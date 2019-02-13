package uk.ac.ebi.uniprot.domain;

import java.util.List;

public interface DBCrossReference<T extends DatabaseType> {
    T getDatabaseType();

    String getId();

    List<Property> getProperties();

    boolean hasDatabaseType();

    boolean hasId();

    boolean hasProperties();

}
