package org.uniprot.core;

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
        throw new RuntimeException("No implementation defined in mock class");
    }

    public @Nullable UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position) {
        return new UniProtDatabaseAttribute(name + position, null, null);
    }

    public String getName() {
        return name;
    }
}
