package org.uniprot.core.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.Property;

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

    protected abstract @Nonnull B getThis();

    protected static <B extends AbstractDBCrossReferenceBuilder<B, T, D>, T extends DatabaseType, D extends DBCrossReference<T>>
    void init(@Nonnull AbstractDBCrossReferenceBuilder<B, T, D> builder, @Nonnull DBCrossReference<T> instance) {
        builder.properties(instance.getProperties())
                .id(instance.getId())
                .databaseType(instance.getDatabaseType());
    }

    public @Nonnull B databaseType(T type) {
        this.databaseType = type;
        return getThis();
    }

    public @Nonnull B id(String id) {
        this.id = id;
        return getThis();
    }

    public @Nonnull B properties(List<Property> properties) {
        this.properties = modifiableList(properties);
        return getThis();
    }

    public @Nonnull B addProperty(Property property) {
        addOrIgnoreNull(property, this.properties);
        return getThis();
    }

    public @Nonnull B addProperty(String key, String value) {
        if (!nullOrEmpty(key) && !nullOrEmpty(value)) {
            this.properties.add(new Property(key, value));
        }
        return getThis();
    }
}
