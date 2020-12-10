package org.uniprot.core.publication;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.Set;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public interface MappedReference extends Serializable {
    UniProtKBAccession getUniProtKBAccession();

    MappedSource getSource();

    String getPubMedId();

    Set<String> getSourceCategories();

    default boolean hasUniProtAccession() {
        return Utils.notNull(getUniProtKBAccession())
                && Utils.notNullNotEmpty(getUniProtKBAccession().getValue());
    }

    default boolean hasSourceCategory() {
        return Utils.notNullNotEmpty(getSourceCategories());
    }
}
