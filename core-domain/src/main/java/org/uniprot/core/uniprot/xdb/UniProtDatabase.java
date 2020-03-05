package org.uniprot.core.uniprot.xdb;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.Database;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

public interface UniProtDatabase extends Database, Serializable {

    @Nonnull
    UniProtDatabaseDetail getDetail();

    @Nullable
    UniProtDatabaseAttribute getAttribute(int position);
}
