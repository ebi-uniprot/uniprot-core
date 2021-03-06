package org.uniprot.core.uniprotkb.description;

import java.util.List;

import org.uniprot.core.util.Utils;

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
