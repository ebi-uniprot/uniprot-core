package org.uniprot.core.uniparc;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public interface InterProGroup extends Serializable {
    String getId();

    String getName();
}
