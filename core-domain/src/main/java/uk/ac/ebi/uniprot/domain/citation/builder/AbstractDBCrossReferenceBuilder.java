package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public abstract class AbstractDBCrossReferenceBuilder<
        B extends AbstractDBCrossReferenceBuilder<B, T, D>,
        T extends DatabaseType,
        D extends DBCrossReference<T>>
        implements Builder2<B, D> {
    protected T databaseType;
    protected String id;
    protected List<Property> properties = new ArrayList<>();

    protected abstract B getThis();

    @Override
    public B from(D instance) {
        return this
                .properties(instance.getProperties())
                .id(instance.getId())
                .databaseType(instance.getDatabaseType());
    }

    public B databaseType(T type) {
        this.databaseType = type;
        return getThis();
    }

    public B id(String id) {
        this.id = id;
        return getThis();
    }

    public B properties(List<Property> properties) {
        nonNullAddAll(properties, this.properties);
        return getThis();
    }

    public B addProperty(Property property) {
        this.properties.add(property);
        return getThis();
    }

    public B addProperty(String key, String value) {
        this.properties.add(new Property(key, value));
        return getThis();
    }
}
