package org.uniprot.core.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Database;
import org.uniprot.core.Property;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public abstract class AbstractCrossReferenceBuilder<
                B extends AbstractCrossReferenceBuilder<B, T, D>,
                T extends Database,
                D extends CrossReference<T>>
        implements Builder<D> {
    protected T database;
    protected String id;
    protected List<Property> properties = new ArrayList<>();

    protected abstract @Nonnull B getThis();

    protected static <
                    B extends AbstractCrossReferenceBuilder<B, T, D>,
                    T extends Database,
                    D extends CrossReference<T>>
            void init(
                    @Nonnull AbstractCrossReferenceBuilder<B, T, D> builder, @Nonnull D instance) {
        builder.propertiesSet(instance.getProperties())
                .id(instance.getId())
                .database(instance.getDatabase());
    }

    public @Nonnull B database(T database) {
        this.database = database;
        return getThis();
    }

    public @Nonnull B id(String id) {
        this.id = id;
        return getThis();
    }

    public @Nonnull B propertiesSet(List<Property> properties) {
        this.properties = modifiableList(properties);
        return getThis();
    }

    public @Nonnull B propertiesAdd(Property property) {
        addOrIgnoreNull(property, this.properties);
        return getThis();
    }

    public @Nonnull B propertiesAdd(String key, String value) {
        if (notNullNotEmpty(key) && notNullNotEmpty(value)) {
            this.properties.add(new Property(key, value));
        }
        return getThis();
    }
}
