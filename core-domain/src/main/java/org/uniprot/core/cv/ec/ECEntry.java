package org.uniprot.core.cv.ec;

import java.io.Serializable;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public interface ECEntry extends Serializable {
    String getId();

    String getLabel();
}
