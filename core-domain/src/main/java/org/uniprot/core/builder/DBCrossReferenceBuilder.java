package org.uniprot.core.builder;

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
    public DBCrossReference<T> build() {
        return new DBCrossReferenceImpl<>(databaseType, id, properties);
    }

    @Override
    protected DBCrossReferenceBuilder<T> getThis() {
        return this;
    }
}
