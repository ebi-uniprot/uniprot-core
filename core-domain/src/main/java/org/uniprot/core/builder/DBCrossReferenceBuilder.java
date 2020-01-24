package org.uniprot.core.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.impl.DBCrossReferenceImpl;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class DBCrossReferenceBuilder<T extends DatabaseType>
        extends AbstractDBCrossReferenceBuilder<
                DBCrossReferenceBuilder<T>, T, DBCrossReference<T>> {
    @Override
    public @Nonnull DBCrossReference<T> build() {
        return new DBCrossReferenceImpl<>(databaseType, id, properties);
    }

    @Override
    protected @Nonnull DBCrossReferenceBuilder<T> getThis() {
        return this;
    }

    public static @Nonnull <T extends DatabaseType> DBCrossReferenceBuilder<T> from(@Nonnull DBCrossReference<T> instance) {
        DBCrossReferenceBuilder<T> builder = new DBCrossReferenceBuilder<>();
        AbstractDBCrossReferenceBuilder.init(builder, instance);
        return builder;
    }
}
