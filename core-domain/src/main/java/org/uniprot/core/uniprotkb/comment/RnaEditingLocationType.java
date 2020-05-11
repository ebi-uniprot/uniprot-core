package org.uniprot.core.uniprotkb.comment;

import static org.uniprot.core.util.Utils.notNullNotEmpty;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum RnaEditingLocationType implements EnumDisplay {
    Not_applicable,
    Undetermined,
    Unknown,
    Known;

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull RnaEditingLocationType typeOf(@Nonnull String locationType) {
        for (RnaEditingLocationType type : RnaEditingLocationType.values()) {
            if (type.getName().equalsIgnoreCase(locationType)) {
                return type;
            } else if (notNullNotEmpty(locationType) && Character.isDigit(locationType.charAt(0))) {
                return Known;
            }
        }
        return Unknown;
    }
}
