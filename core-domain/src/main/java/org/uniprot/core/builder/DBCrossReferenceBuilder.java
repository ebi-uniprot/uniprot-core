package org.uniprot.core.builder;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.impl.DBCrossReferenceImpl;

import javax.annotation.Nonnull;

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
}
