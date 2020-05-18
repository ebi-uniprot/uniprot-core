package org.uniprot.core.xml.utils;

import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.util.Utils;


import java.math.BigInteger;
import java.util.Objects;

/**
 * @author lgonzales
 * @since 18/05/2020
 */
public class FeatureUtils {

    public static final String GREATER_THAN = "greater than";
    public static final String LESS_THAN = "less than";
    public static final String UNKNOWN = "unknown";
    public static final String UNCERTAIN = "uncertain";

    private FeatureUtils(){

    }

    public static boolean locationIsSame(Range location, PositionModifier modifier) {
        boolean isModifierSame =
                (location.getStart().getModifier() == modifier) &&
                        (location.getEnd().getModifier() == modifier);
        if (modifier == PositionModifier.UNKNOWN){
            return isModifierSame;
        } else {
            if (!isModifierSame){
                return isModifierSame;
            } else {
                return Objects.equals(location.getStart().getValue(), location.getEnd().getValue());
            }
        }
    }

    public static Position positionfromXml(BigInteger position, String status, String outsideString) {
        PositionModifier modifier = PositionModifier.EXACT;
        if (Utils.notNullNotEmpty(status)) {
            if (status.equals(UNKNOWN)) {
                modifier = PositionModifier.UNKNOWN;
                return new Position(-1, modifier);
            } else if (status.equals(UNCERTAIN)) {
                modifier = PositionModifier.UNSURE;
            } else if (status.equals(outsideString)) {
                modifier = PositionModifier.OUTSIDE;
            }
        }
        return new Position(position.intValue(), modifier);
    }
}
