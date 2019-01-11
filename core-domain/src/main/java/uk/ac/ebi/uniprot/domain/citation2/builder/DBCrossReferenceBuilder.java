package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class DBCrossReferenceBuilder<T extends DatabaseType> implements Builder<DBCrossReferenceBuilder<T>, DBCrossReference<T>> {
    private T databaseType;
    private String id;
    private List<Property> properties = new ArrayList<>();

    @Override
    public DBCrossReference<T> build() {
        return new DBCrossReferenceImpl<>(this);
    }

    @Override
    public DBCrossReferenceBuilder<T> from(DBCrossReference<T> instance) {
        return new DBCrossReferenceBuilder<T>()
                .properties(instance.getProperties())
                .id(instance.getId())
                .databaseType(instance.getDatabaseType());
    }

    public DBCrossReferenceBuilder<T> databaseType(T type) {
        this.databaseType = type;
        return this;
    }

    public DBCrossReferenceBuilder<T> id(String id) {
        this.id = id;
        return this;
    }

    public DBCrossReferenceBuilder<T> properties(List<Property> properties) {
        this.properties.addAll(properties);
        return this;
    }

    public DBCrossReferenceBuilder<T> addProperty(Property property) {
        this.properties.add(property);
        return this;
    }

    public DBCrossReferenceBuilder<T> addProperty(String key, String value) {
        this.properties.add(new Property(key, value));
        return this;
    }

    public T getDatabaseType() {
        return databaseType;
    }

    public String getId() {
        return id;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
