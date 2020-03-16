package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;
import org.uniprot.core.util.Utils;

public interface MaximumVelocity extends HasEvidences {
    double getVelocity();

    String getEnzyme();

    String getUnit();

    default boolean hasVelocity() {
        return getVelocity() > 0.0d;
    }

    default boolean hasEnzyme() {
        return Utils.notNullNotEmpty(getEnzyme());
    }

    default boolean hasUnit() {
        return Utils.notNullNotEmpty(getUnit());
    }
}
