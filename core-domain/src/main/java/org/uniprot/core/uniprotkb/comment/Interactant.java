package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

import java.io.Serializable;

public interface Interactant extends Serializable {
    UniProtKBAccession getUniProtKBAccession();

    String getGeneName();

    String getChainId();

    String getIntActId();

    default boolean hasUniProtKBAccession() {
        return Utils.notNull(getUniProtKBAccession());
    }

    default boolean hasGeneName() {
        return Utils.notNullNotEmpty(getGeneName());
    }

    default boolean hasChainId() {
        return Utils.notNullNotEmpty(getChainId());
    }

    default boolean hasIntActId() {
        return Utils.notNullNotEmpty(getIntActId());
    }
}
