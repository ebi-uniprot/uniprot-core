package uk.ac.ebi.uniprot.domain.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class DBCrossReferenceBuilder<T extends DatabaseType> extends AbstractDBCrossReferenceBuilder<DBCrossReferenceBuilder<T>, T, DBCrossReference<T>> {
    @Override
    public DBCrossReference<T> build() {
        return new DBCrossReferenceImpl<>(databaseType, id, properties);
    }

    @Override
    protected DBCrossReferenceBuilder<T> getThis() {
        return this;
    }
}