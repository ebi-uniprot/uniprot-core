package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * @author sahmad
 */
public interface Information extends Serializable {
    @Nonnull
    String getVersion();
    @Nullable
    String getComment();
    @Nullable
    String getOldRuleNum();
    @Nullable
    List<UniProtKBId> getUniProtIds();
    @Nullable
    DomainClassType getDomainClass();







}
