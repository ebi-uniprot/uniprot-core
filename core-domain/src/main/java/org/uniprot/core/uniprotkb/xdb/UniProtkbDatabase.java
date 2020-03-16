package org.uniprot.core.uniprotkb.xdb;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.Database;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

public interface UniProtkbDatabase extends Database, Serializable {

    @Nonnull
    UniProtDatabaseDetail getUniProtDatabaseDetail();

    @Nullable
    UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position);
}
