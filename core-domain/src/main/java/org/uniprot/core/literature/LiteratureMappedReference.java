package org.uniprot.core.literature;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 */
public interface LiteratureMappedReference extends Serializable {

    UniProtAccession getUniprotAccession();

    String getSource();

    String getSourceId();

    List<String> getSourceCategory();

    String getAnnotation();

    default boolean hasUniprotAccession() {
        return Utils.nonNull(getUniprotAccession()) && Utils.notNullOrEmpty(getUniprotAccession().getValue());
    }

    default boolean hasSource() {
        return Utils.notNullOrEmpty(getSource());
    }

    default boolean hasSourceId() {
        return Utils.notNullOrEmpty(getSourceId());
    }

    default boolean hasSourceCategory() {
        return Utils.notNullOrEmpty(getSourceCategory());
    }

    default boolean hasAnnotation() {
        return Utils.notNullOrEmpty(getAnnotation());
    }
}
