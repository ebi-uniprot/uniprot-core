package org.uniprot.core.publication;

import java.io.Serializable;
import java.time.LocalDate;

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

    LocalDate getSubmissionDate();
}
