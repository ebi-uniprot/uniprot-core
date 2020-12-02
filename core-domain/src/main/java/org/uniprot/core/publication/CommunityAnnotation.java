package org.uniprot.core.publication;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public interface CommunityAnnotation {
    String getProteinOrGene();

    String getFunction();

    String getDisease();

    String getComment();
}
