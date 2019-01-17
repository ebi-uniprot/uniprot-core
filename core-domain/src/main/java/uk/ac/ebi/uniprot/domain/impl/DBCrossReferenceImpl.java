package uk.ac.ebi.uniprot.domain.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DBCrossReferenceImpl<T extends DatabaseType> implements DBCrossReference<T> {
    private T databaseType;
    private String id;
    private List<Property> properties;

    private DBCrossReferenceImpl() {
        this.properties = Collections.emptyList();
    }

    public DBCrossReferenceImpl(T database, String id) {
        this(new DBCrossReferenceBuilder<T>()
                     .databaseType(database)
                     .id(id));
    }

    public DBCrossReferenceImpl(DBCrossReferenceBuilder<T> builder) {
        this.databaseType = builder.getDatabaseType();
        this.id = builder.getId();
        setProperties(Collections.unmodifiableList(builder.getProperties()));
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((databaseType == null) ? 0 : databaseType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        DBCrossReferenceImpl other = (DBCrossReferenceImpl) obj;
        if (databaseType == null) {
            if (other.databaseType != null)
                return false;
        } else if (!databaseType.equals(other.databaseType))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (properties == null) {
            if (other.properties != null)
                return false;
        } else if (!properties.equals(other.properties))
            return false;
        return true;
    }


}
