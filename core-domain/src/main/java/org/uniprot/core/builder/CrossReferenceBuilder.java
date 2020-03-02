package org.uniprot.core.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Database;
import org.uniprot.core.impl.CrossReferenceImpl;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class CrossReferenceBuilder<T extends Database>
        extends AbstractCrossReferenceBuilder<CrossReferenceBuilder<T>, T, CrossReference<T>> {
    @Override
    public @Nonnull CrossReference<T> build() {
        return new CrossReferenceImpl<>(databaseType, id, properties);
    }

    @Override
    protected @Nonnull CrossReferenceBuilder<T> getThis() {
        return this;
    }

    public static @Nonnull <T extends Database> CrossReferenceBuilder<T> from(
            @Nonnull CrossReference<T> instance) {
        CrossReferenceBuilder<T> builder = new CrossReferenceBuilder<>();
        AbstractCrossReferenceBuilder.init(builder, instance);
        return builder;
    }
}
