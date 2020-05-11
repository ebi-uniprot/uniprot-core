package org.uniprot.core.feature;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public interface AlternativeSequence extends Serializable {

    String getOriginalSequence();

    List<String> getAlternativeSequences();
}
