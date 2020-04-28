package org.uniprot.core;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

public class UniProtKBDatabaseMock implements UniProtKBDatabase {
    private String name;

    public UniProtKBDatabaseMock(String name) {
        this.name = name;
    }

    public @Nonnull UniProtDatabaseDetail getUniProtDatabaseDetail() {
        return new UniProtDatabaseDetail("dummy", "dummyName", null,
                null, null, false, null);
    }

    public @Nullable UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position) {
        return new UniProtDatabaseAttribute(name + position, null, null);
    }

    public String getName() {
        return name;
    }

    @Override // Warning: don't rely on this method
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        UniProtKBDatabase that = (UniProtKBDatabase) o;
        return Objects.equals(name, that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
