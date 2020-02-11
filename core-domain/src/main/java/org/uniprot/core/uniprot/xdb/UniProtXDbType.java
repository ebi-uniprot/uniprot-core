package org.uniprot.core.uniprot.xdb;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.cv.xdb.DBXRefTypeAttribute;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;

public interface UniProtXDbType extends DatabaseType, Serializable {

    @Nonnull
    UniProtXDbTypeDetail getDetail();

    @Nullable
    DBXRefTypeAttribute getAttribute(int position);
}
