package org.uniprot.core.uniprotkb.interaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public interface InteractionEntry extends Serializable {
    List<InteractionMatrixItem> getInteractionMatrix();
}
