package org.uniprot.core.uniprot.comment;

import org.uniprot.core.uniprot.evidence.HasEvidences;
import org.uniprot.core.util.Utils;

public interface MaximumVelocity extends HasEvidences {
    double getVelocity();

    String getEnzyme();

    String getUnit();

    default boolean hasVelocity() {
        return getVelocity() > 0.0d;
    }

    default boolean hasEnzyme() {
        return Utils.notNullOrEmpty(getEnzyme());
    }

    default boolean hasUnit() {
        return Utils.notNullOrEmpty(getUnit());
    }
}
