package org.uniprot.core.publication;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public interface PublicationMappedReference extends Serializable {
    UniProtKBAccession getUniProtKBAccession();

    String getSource();

    String getSourceId();

    String getPubMedId();

    List<String> getSourceCategories();

    default boolean hasUniProtAccession() {
        return Utils.notNull(getUniProtKBAccession())
                && Utils.notNullNotEmpty(getUniProtKBAccession().getValue());
    }

    default boolean hasSource() {
        return Utils.notNullNotEmpty(getSource());
    }

    default boolean hasSourceId() {
        return Utils.notNullNotEmpty(getSourceId());
    }

    default boolean hasSourceCategory() {
        return Utils.notNullNotEmpty(getSourceCategories());
    }

}
