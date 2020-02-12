package org.uniprot.cv.xdb;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.cv.xdb.DBXRefTypeAttribute;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;

public final class UniProtXDbTypeImpl implements UniProtXDbType {
    private static final long serialVersionUID = 201534956573963997L;
    private String name;

    private UniProtXDbTypeImpl() {}

    public UniProtXDbTypeImpl(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getName() {
        return name;
    }

    public @Nonnull UniProtXDbTypeDetail getDetail() {
        return UniProtXDbTypes.INSTANCE.getType(name);
    }

    public @Nullable DBXRefTypeAttribute getAttribute(int position) {
        DBXRefTypeAttribute toReturn = null;
        List<DBXRefTypeAttribute> attributes =
                UniProtXDbTypes.INSTANCE.getType(name).getAttributes();
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
        UniProtXDbType other = (UniProtXDbType) obj;
        if (name == null) {
            return other.getName() == null;
        } else return name.equals(other.getName());
    }
}
