package org.uniprot.cv.xdb;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;

public final class UniProtDatabaseImpl implements UniProtDatabase {
    private static final long serialVersionUID = 201534956573963997L;
    private String name;

    private UniProtDatabaseImpl() {}

    public UniProtDatabaseImpl(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getName() {
        return name;
    }

    public @Nonnull UniProtDatabaseDetail getUniProtDatabaseDetail() {
        return UniProtDatabaseTypes.INSTANCE.getDbTypeByName(name);
    }

    public @Nullable UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position) {
        UniProtDatabaseAttribute toReturn = null;
        List<UniProtDatabaseAttribute> attributes =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName(name).getAttributes();
        if (attributes.size() > position) {
            toReturn = attributes.get(position);
        }
        return toReturn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UniProtDatabase other = (UniProtDatabase) obj;
        if (name == null) {
            return other.getName() == null;
        } else return name.equals(other.getName());
    }
}
