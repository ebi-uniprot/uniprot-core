package org.uniprot.core.publication;

import java.io.Serializable;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public interface CommunityAnnotation extends Serializable {
    String getProteinOrGene();

    String getFunction();

    String getDisease();

    String getComment();
}
