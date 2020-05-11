package org.uniprot.core.literature;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.List;

/** @author lgonzales */
public interface LiteratureMappedReference extends Serializable {

    UniProtKBAccession getUniprotAccession();

    String getSource();

    String getSourceId();

    List<String> getSourceCategories();

    String getAnnotation();

    default boolean hasUniprotAccession() {
        return Utils.notNull(getUniprotAccession())
                && Utils.notNullNotEmpty(getUniprotAccession().getValue());
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

    default boolean hasAnnotation() {
        return Utils.notNullNotEmpty(getAnnotation());
    }
}
