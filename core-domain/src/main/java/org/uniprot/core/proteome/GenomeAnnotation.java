package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 * @author lgonzales
 * @since 12/11/2020
 */
public interface GenomeAnnotation extends Serializable {

    String getSource();

    String getUrl();

}
