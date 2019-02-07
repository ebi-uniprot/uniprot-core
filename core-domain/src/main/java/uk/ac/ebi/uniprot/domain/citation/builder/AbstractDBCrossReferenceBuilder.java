package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.*;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public abstract class AbstractDBCrossReferenceBuilder<
        B extends AbstractDBCrossReferenceBuilder<B, T, D>,
        T extends DatabaseType,
        D extends DBCrossReference<T>>
        implements Builder<B, D> {
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
        this.properties = nonNullList(properties);
        return getThis();
    }

    public B addProperty(Property property) {
        nonNullAdd(property, this.properties);
        return getThis();
    }

    public B addProperty(String key, String value) {
        if (!nullOrEmpty(key) && !nullOrEmpty(value)) {
            this.properties.add(new Property(key, value));
        }
        return getThis();
    }
}
