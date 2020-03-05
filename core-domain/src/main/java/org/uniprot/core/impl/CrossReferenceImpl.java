package org.uniprot.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Database;
import org.uniprot.core.Property;
import org.uniprot.core.util.Utils;

public class CrossReferenceImpl<T extends Database> implements CrossReference<T> {
    private static final long serialVersionUID = 4318477387676269483L;
    protected T databaseType;
    protected String id;
    protected List<Property> properties;

    // no arg constructor for JSON deserialization
    CrossReferenceImpl() {
        this.properties = Collections.emptyList();
    }

    public CrossReferenceImpl(T database, String id) {
        this(database, id, Collections.emptyList());
    }

    public CrossReferenceImpl(T databaseType, String id, List<Property> properties) {
        this.databaseType = databaseType;
        this.id = id;
        setProperties(properties);
    }

    @Override
    public T getDatabase() {
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
    public boolean hasDatabase() {
        return this.databaseType != null;
    }

    @Override
    public boolean hasId() {
        return Utils.notNullNotEmpty(this.id);
    }

    @Override
    public boolean hasProperties() {
        return Utils.notNullNotEmpty(this.properties);
    }

    public void setProperties(List<Property> properties) {
        if ((properties == null) || properties.isEmpty()) this.properties = Collections.emptyList();
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
        CrossReferenceImpl<?> that = (CrossReferenceImpl<?>) o;
        return Objects.equals(databaseType, that.databaseType)
                && Objects.equals(id, that.id)
                && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseType, id, properties);
    }
}
