package org.uniprot.core.uniprotkb.xdb;

import org.uniprot.core.Database;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UniProtKBDatabase extends Database, Serializable {

    @Nonnull
    UniProtDatabaseDetail getUniProtDatabaseDetail();

    @Nullable
    UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position);
}
