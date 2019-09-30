package org.uniprot.core.uniprot.comment;

import org.uniprot.core.util.EnumDisplay;

/** Created by IntelliJ IDEA. User: spatient Date: 03-Feb-2009 Time: 17:21:39 */
public enum RnaEditingLocationType implements EnumDisplay<RnaEditingLocationType> {
    Not_applicable,
    Undetermined,
    Unknown,
    Known;

    public static RnaEditingLocationType getType(String locationType) {
        for (RnaEditingLocationType type : RnaEditingLocationType.values()) {
            if (type.name().equals(locationType)) {
                return type;
            } else if (locationType != null
                    && locationType.length() > 0
                    && Character.isDigit(locationType.charAt(0))) {
                return Known;
            }
        }
        return Unknown;
    }

    @Override
    public String toDisplayName() {
        return name();
    }
}
