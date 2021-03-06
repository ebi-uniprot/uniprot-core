package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 20 Nov 2019
 */
public interface HasMolecule {
    String getMolecule();

    default boolean hasMolecule() {
        return Utils.notNullNotEmpty(getMolecule());
    }
}
