package uk.ac.ebi.uniprot.domain.literature;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.common.Utils;

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
        return Utils.nonNull(getUniprotAccession()) && Utils.notEmpty(getUniprotAccession().getValue());
    }

    default boolean hasSource() {
        return Utils.notEmpty(getSource());
    }

    default boolean hasSourceId() {
        return Utils.notEmpty(getSourceId());
    }

    default boolean hasSourceCategory() {
        return Utils.notEmpty(getSourceCategory());
    }

    default boolean hasAnnotation() {
        return Utils.notEmpty(getAnnotation());
    }
}
