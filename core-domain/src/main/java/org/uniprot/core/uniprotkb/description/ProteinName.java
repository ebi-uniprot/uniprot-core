package org.uniprot.core.uniprotkb.description;

import org.uniprot.core.util.Utils;

import java.util.List;

/**
 * Created 19/11/2020
 *
 * @author Edd
 */
public interface ProteinName extends ProteinSubName {
    List<Name> getShortNames();

    default boolean hasShortNames() {
        return Utils.notNullNotEmpty(getShortNames());
    }
}
