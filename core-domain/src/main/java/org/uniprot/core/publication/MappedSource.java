package org.uniprot.core.publication;

import java.io.Serializable;

/**
 * Created 03/12/2020
 *
 * @author Edd
 */
public interface MappedSource extends Serializable {
    String getName();

    String getId();
}
