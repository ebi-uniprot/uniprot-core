package uk.ac.ebi.uniprot.domain.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class DBCrossReferenceImpl<T extends DatabaseType> implements DBCrossReference<T> {
    private static final long serialVersionUID = 4318477387676269483L;
    protected T databaseType;
    protected String id;
    protected List<Property> properties;

    private DBCrossReferenceImpl() {
        this.properties = Collections.emptyList();
    }

    public DBCrossReferenceImpl(T database, String id) {
        this(database, id, Collections.emptyList());
    }

    public DBCrossReferenceImpl(T databaseType, String id, List<Property> properties) {
        this.databaseType = databaseType;
        this.id = id;
        setProperties(properties);
    }

    @Override
    public T getDatabaseType() {
        return databaseType;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public boolean hasDatabaseType() {
        return this.databaseType != null;
    }

    @Override
    public boolean hasId() {
        return Utils.notEmpty(this.id);
    }

    @Override
    public boolean hasProperties() {
        return Utils.notEmpty(this.properties);
    }

    public void setProperties(List<Property> properties) {
        if ((properties == null) || properties.isEmpty())
            this.properties = Collections.emptyList();
        else {
            this.properties = new ArrayList<>();
            this.properties.addAll(properties);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.databaseType.getName()).append(":").append(id);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBCrossReferenceImpl<?> that = (DBCrossReferenceImpl<?>) o;
        return Objects.equals(databaseType, that.databaseType) &&
                Objects.equals(id, that.id) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseType, id, properties);
    }
}
